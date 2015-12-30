/*********************************************************************
 * Author: Malte Helmert (helmert@informatik.uni-freiburg.de)
 * (C) Copyright 2003-2004 Malte Helmert
 * Modified by: Silvia Richter (silvia.richter@nicta.com.au)
 * (C) Copyright 2008 NICTA
 *
 * This file is part of LAMA.
 *
 * LAMA is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the license, or (at your option) any later version.
 *
 * LAMA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 *
 *********************************************************************/

#include <algorithm>
#include "best_first_search.h"
#include "globals.h"
#include "heuristic.h"
#include "successor_generator.h"
#include "operator.h"
#include "ff_heuristic.h"
#include "landmarks_count_heuristic.h"

#ifdef STOC
#include "mrw.h"
//#include "mrwff_heuristic.h"
//#include <pthread.h>
#endif
#include <climits>
#include <cstdlib>
#include <cassert>
using namespace std;

OpenListInfo::OpenListInfo(Heuristic *heur, bool only_pref) {
	heuristic = heur;
	only_preferred_operators = only_pref;
	priority = 0;
}

OpenListEntry::OpenListEntry(const State *_parent, const Operator *_op) {
//		int _parent_heur) {
	parent = _parent;
	op = _op;
//	parent_heur = _parent_heur;
  expanded = false;
}

BestFirstSearchEngine::BestFirstSearchEngine() :
	current_state(*g_initial_state) {
	generated_states = 0;
	current_predecessor = 0;
	current_operator = 0;
	current_heur = 0;
	plateau = 0;
  g_find_plateau = false;
  milestone = 0;
}

BestFirstSearchEngine::~BestFirstSearchEngine() {
}

void BestFirstSearchEngine::add_heuristic(Heuristic *heuristic,
		bool use_estimates, bool use_preferred_operators) {
	assert(use_estimates || use_preferred_operators);
	heuristics.push_back(heuristic);
	best_heuristic_values.push_back(-1);
  /* Lu 
   * heuristics in open_lists: LAMA-prefer, FF-prefer, FF-prefer(no-metric), FF-non-prefer
   * heuristics in best_heuristic_values: LAMA, FF, FF(no-metric)
   * */
  open_lists.push_back(OpenListInfo(heuristic, false));
  if(use_preferred_operators)
    open_lists.push_back(OpenListInfo(heuristic, true));
    

	if (heuristic->get_h_type() == FF) {
    //Lu add a open queue for ff-nometric
    if(heuristic->use_metric == true && g_use_multi_ff == true){
      best_heuristic_values.push_back(-1);
		  open_lists.push_back(OpenListInfo(heuristic, true));
    }
		//open_lists.push_back(OpenListInfo(heuristic, false));
	}
	if (use_preferred_operators)
		preferred_operator_heuristics.push_back(heuristic);
}

void BestFirstSearchEngine::initialize() {
	// cout << "Conducting best first search." << endl;
	assert(!open_lists.empty());
  total_min_heu_val = INT_MAX;
  total_min_step_cost = INT_MAX;
}

void BestFirstSearchEngine::statistics() const {
	cout << "Expanded " << closed_list.size() << " state(s)." << endl;
	cout << "Generated " << generated_states << " state(s)." << endl;
}

int BestFirstSearchEngine::step() {
	// Invariants:
	// - current_state is the next state for which we want to compute the heuristic.
	// - current_predecessor is a permanent pointer to the predecessor of that state.
	// - current_operator is the operator which leads to current_state from predecessor.

	if (g_found_solution) {
		cout << "[LAMA] Respect solutions from other threads\n";
		return SOLVED;
	}

	if (!closed_list.contains(current_state)) {
		const State *parent_ptr = closed_list.insert(current_state,	current_predecessor, current_operator);
    //cout << "[LAMA] current states number in closed list: " << closed_list.size() << endl;

		if (g_lm_heur != NULL)
			g_lm_heur->set_recompute_heuristic(current_state);
		if (g_ff_heur != NULL)
			g_ff_heur->set_recompute_heuristic();
		for (int i = 0; i < heuristics.size(); i++){
			heuristics[i]->evaluate(current_state);
    }
    milestone++;
		if (!is_dead_end()) {
			if (check_goal(current_state))
				return SOLVED;
			//Lu
      generate_successors(parent_ptr);
			
      if (check_progress(parent_ptr)) {
				report_progress();
				reward_progress();
			}
		}
	}
	return fetch_next_state();
}

bool BestFirstSearchEngine::is_dead_end() {
	// If a reliable heuristic reports a dead end, we trust it.
	// Otherwise, all heuristics must agree on dead-end-ness.
	int dead_end_counter = 0;
	for (int i = 0; i < heuristics.size(); i++) {
		if (heuristics[i]->is_dead_end()) {
			if (heuristics[i]->dead_ends_are_reliable())
				return true;
			else
				dead_end_counter++;
		}
	}
	return dead_end_counter == heuristics.size();
}

bool BestFirstSearchEngine::check_goal(State & s) {
	// Any heuristic reports 0 if this is a goal state, so we can
	// pick an arbitrary one.
	Heuristic *heur = open_lists[0].heuristic;
	if (!heur->is_dead_end() && heur->get_heuristic() == 0) {
		// We actually need this silly !heur->is_dead_end() check because
		// this state *might* be considered a non-dead end by the
		// overall search even though heur considers it a dead end
		// (e.g. if heur is the CG heuristic, but the FF heuristic is
		// also computed and doesn't consider this state a dead end.
		// If heur considers the state a dead end, it cannot be a goal
		// state (heur will not be *that* stupid). We may not call
		// get_heuristic() in such cases because it will barf.

		// If (and only if) using action costs the heuristic might report 0
		// even though the goal is not reached - check.

		//if (g_use_metric) //delete by Lu
			for (int i = 0; i < g_goal.size(); i++)
				if (s[g_goal[i].first] != g_goal[i].second)
					return false;
		cout << "[LAMA] Solution found!" << endl;
		g_found_solution = true;
		Plan plan;
    //Lu
		trace_path(s, plan);
		set_plan(plan);
    /*
		Trajectory t;
		closed_list.trace_state_path(s, t);
		set_trajectory(t);
    */
		return true;
	} else {
		return false;
	}
}

#ifdef STOC
void* conduct_mrw_search(MRWMessage* m);
//add by Lu begin
bool BestFirstSearchEngine::call_mrw(const State* s) {
  //assert(g_stoc_instances == 1);
  bool progress = false;
  int num_MRW_plans = g_MRWPlan.size();
	//int ret;
  g_mrw_stop = 2; // when two threads find better states, then stop all threads
#ifdef VERBOSE
  cout << "[LAMA] call MRW to expand neighborhood states" << endl;
#endif
	for (int i = 0; i < g_stoc_instances; i++) {
    g_engine_messages[i].init_state = s;
    //g_engine_messages[i].heu_bound = total_min_heu_val;
    g_engine_messages[i].step_cost = total_min_step_cost;
    if(g_mrw_stop > 0){
      conduct_mrw_search(&g_engine_messages[i]);
    }
    /*
		ret = pthread_create(&g_threads[i], NULL, conduct_mrw_search,	(void*) &g_engine_messages[i]);
		if (ret)
			cout << "[ERROR] Error code is " << i << " in creating MRW threads" << ret << endl;
      */
	}
/*
	for (int i = 0; i < g_stoc_instances; i++) {
		pthread_join(g_threads[i], NULL);
	}
*/
#ifdef VERBOSE
  cout << "[LAMA] MRW runs over" << endl;
#endif
  //expand the paths search by MRWs
  while(num_MRW_plans < g_MRWPlan.size()) { 
    //MRW has expanded some paths
    MRWPlan const * p = g_MRWPlan[num_MRW_plans];
    State t_current_state = State(*p->init_state, *(p->plan[0]));
    State const * p_current_predecessor = p->init_state;
    Operator const * p_current_operator = p->plan[0];
    for (int i = 1; i < p->plan.size(); ++i) {
		  t_current_state = State(t_current_state, *(p->plan[i]));
      p_current_operator = p->plan[i];
	  }
    t_current_state.set_MRW_id(num_MRW_plans);

    if (!closed_list.contains(t_current_state)) {
      const State *p_parent_ptr = closed_list.insert(t_current_state,
          p_current_predecessor, p_current_operator);
      
		  if (g_lm_heur != NULL)
			  g_lm_heur->set_recompute_heuristic(t_current_state);
		  if (g_ff_heur != NULL)
			  g_ff_heur->set_recompute_heuristic();
		  for (int i = 0; i < heuristics.size(); i++)
			  heuristics[i]->evaluate(t_current_state);
		
      if (!is_dead_end()) {
			  if (check_goal(t_current_state)){
          g_found_solution = true; // use this to stop LAMA searc
          return progress;
        }
        if(heuristics[1]->get_step_cost() < total_min_step_cost) {
#ifdef VERBOSE
          cout <<"[LAMA] insert a valuable path (" << p->plan.size() <<" steps)" << endl;
#endif
          progress = true;
        }
			  generate_successors(p_parent_ptr);
			  /*if (check_progress((State*) parent_ptr)) {
				  report_progress();
				  reward_progress();
			  }*/
      }
    }
    num_MRW_plans++;
  }
  return progress;
}
//add by Lu end
#endif

bool BestFirstSearchEngine::check_progress(const State* s) {
	bool progress = false;
	//milestone++;
#ifdef HACK
  g_last_open = 0;
#endif
	for (int i = 0; i < heuristics.size(); i++) {
		if (heuristics[i]->is_dead_end())
			continue;
		int h = heuristics[i]->get_heuristic();
		int &best_h = best_heuristic_values[i];
		if (best_h == -1 || h < best_h) {
			best_h = h;
			progress = true;

			milestone = 0;
#ifdef HACK
      g_last_open |= 1 << ((i*2)+1) ;
      //g_last_open |= 1 << ((i*2)+1) ;
#endif
      if(heuristics[i]->get_h_type() == FF && (heuristics[i]->use_metric == false || g_use_multi_ff == false)){
        if( total_min_step_cost > h ){
          total_min_step_cost = h;
        }
      }
      /*
      //Lu Because MRW only use FF heuristic, we only count FF heu value.
      if(heuristics[i]->get_h_type() == FF){
        if( total_min_heu_val > h )
          total_min_heu_val = h;
      }*/
    }
    //Add by Lu
    if(heuristics[i]->get_h_type() == FF && heuristics[i]->use_metric == true && g_use_multi_ff == true){
      assert(i == 1);
      int h = heuristics[i]->get_step_cost();
      int &best_h = best_heuristic_values[i+1];
      if (best_h == -1 || h < best_h) {
        best_h = h;
        progress = true;
        milestone = 0;
#ifdef HACK
        g_last_open |= 1 << 2*(i+1);
#endif
        if( total_min_step_cost > h )
          total_min_step_cost = h;
      }
    }
	}
	/* Added by Eric */
  //Lu
  if(progress || g_stoc_instances == 0 )
    return progress;

  //Lu call MRW to expolre local neighborhood states
  if (milestone > PLATEAU_SIZE) {
#ifdef VERBOSE
		cout << "[LAMA] Detect a local minimal point: did not have progress in " << PLATEAU_SIZE << " states"	<< endl;
#endif
    PLATEAU_SIZE = PLATEAU_SIZE > MAX_PLATEAU_SIZE? MAX_PLATEAU_SIZE/4  : PLATEAU_SIZE+1000;
    if(call_mrw(s))
      milestone -= PLATEAU_SIZE / 2;
    else
      milestone = 0;
  }	
  return progress;
}

void BestFirstSearchEngine::report_progress() {
	cout << "[LAMA] Best heuristic value: ";
  //Lu
	for (int i = 0; i < best_heuristic_values.size(); i++) {
		cout << best_heuristic_values[i];
		if (i != best_heuristic_values.size() - 1)
			cout << "/";
	}
  cout << " priority: ";
  for (int i = 0; i < open_lists.size(); i++){
		cout << abs(open_lists[i].priority);
		if (i != open_lists.size() - 1)
			cout << "/";
	}
	//cout << " [expanded " << closed_list.size() << " state(s)] [" << open_lists[0].open.size() <<"/"<< open_lists[1].open.size() <<"]" << endl;
	cout << " [expanded " << closed_list.size() << " state(s)]" << endl;
}

void BestFirstSearchEngine::reward_progress() {
	// Boost the "preferred operator" open lists somewhat whenever
	// progress is made. This used to be used in multi-heuristic mode
	// only, but it is also useful in single-heuristic mode, at least
	// in Schedule.
	//
	// TODO: Test the impact of this, and find a better way of rewarding
	// successful exploration. For example, reward only the open queue
	// from which the good state was extracted and/or the open queues
	// for the heuristic for which a new best value was found.
	for (int i = 0; i < open_lists.size(); i++){
		if (open_lists[i].only_preferred_operators){
			open_lists[i].priority -= 1000; //Lu
    }
#ifdef HACK   
    if(((g_last_open >> i)  & 1) == 1){
      open_lists[i].priority--;// -= 10;
    }
#endif
  }
}

void BestFirstSearchEngine::generate_successors(const State *parent_ptr) {
	vector<const Operator *> all_operators;
  //Lu
	//g_successor_generator->generate_applicable_ops(current_state, all_operators);
	g_successor_generator->generate_applicable_ops(*parent_ptr, all_operators);

	vector<const Operator *> preferred_operators;
	for (int i = 0; i < preferred_operator_heuristics.size(); i++) {
		Heuristic *heur = preferred_operator_heuristics[i];
		if (!heur->is_dead_end())
			heur->get_preferred_operators(preferred_operators);
	}

  
  //Lu store ops' index in bfd_open queue
	for (int j = 0; j < all_operators.size(); j++) {
    assert(all_operators[j]->is_applicable(*parent_ptr)); //Add by Lu
    ops_index[all_operators[j]] = bfs_open.size();
    bfs_open.push_back(OpenListEntry(parent_ptr, all_operators[j]));
  }

  int h;
	for (int i = 0; i < open_lists.size(); i++) {
		Heuristic *heur = open_lists[i].heuristic;
		if (!heur->is_dead_end()) {
      //Lu insert states to ff-no-metric open queue
      if(i == 4)// heur->use_metric == true && i >= 4)
        h = heur->get_step_cost();
      else
        h = heur->get_heuristic();

			OpenList<int> &open = open_lists[i].open;
			vector<const Operator *> &ops =	open_lists[i].only_preferred_operators ? preferred_operators : all_operators;
			for (int j = 0; j < ops.size(); j++) {
				// Tie breaker criterium ensures breadth-first search on plateaus
				// (will be equal to depth of node if no action costs are used,
				// and cost of node otherwise)
        //Lu for debug
        /*if(!ops[j]->is_applicable(*parent_ptr)){
          cout << "[LAMA] " << ops[j]->get_name() << " is not applicable/MRW" << parent_ptr->get_MRW_id()<< endl;
          continue;
        }*/
				int tie_braker = parent_ptr->get_g_value() + ops[j]->get_cost();
        int id = ops_index[ops[j]];
				//open.insert(make_pair(h, tie_braker), OpenListEntry(parent_ptr, ops[j], h));
				open.insert(make_pair(h, tie_braker), id);
			}
		}
	}
	generated_states += all_operators.size();
}

int BestFirstSearchEngine::fetch_next_state() {
  //Add by Lu
  int next_id = -1;
  while(true){
    OpenListInfo *open_info = select_open_queue();
    if (!open_info) {
      cout << "Completely explored state space -- no solution!" << endl;
      return FAILED;
    }

  	next_id = open_info->open.remove_min();
    if(bfs_open[next_id].expanded == false){
      open_info->priority++;
      break;
    }else
      g_expanded_hits++;
  }
	assert(next_id >=0 && next_id < bfs_open.size());
  OpenListEntry next = bfs_open[next_id];
  
  next.expanded = true;
	current_predecessor = next.parent;
	current_operator = next.op;
	current_state = State(*current_predecessor, *current_operator);
	return IN_PROGRESS;
}

/* TODO 
	avoid jumping around between open_queues by setting a 
	relatively flexible queue exploration standard */
OpenListInfo *BestFirstSearchEngine::select_open_queue() {
	int current_open = 0;
	OpenListInfo *best = NULL;
	for (int i = 0; i < open_lists.size(); i++){
		if (!open_lists[i].open.empty() && (best == 0 || open_lists[i].priority
				< best->priority))
			{	
				best = &open_lists[i];
				current_open = i;
			}
  }
	#ifdef HACK
	/*if (g_last_open != current_open && best != NULL) 
	{
		best->priority-=10;	// 10 states per time to make open-pick more stable
	}*/
	g_last_open = current_open;
	#endif
	return best;
}
//Add by Lu
void BestFirstSearchEngine::trace_path(State & s, Plan & p) {
  int MRW_id = -1;
  State * current_s = NULL;
  State * parent_s = &s;
  while( true ){
    current_s = parent_s;
    MRW_id = int (current_s->get_MRW_id()); 
    if( MRW_id == -1){
      parent_s = (State * )closed_list.trace_one_step(*current_s, p);
    }else{
      MRWPlan const * MRW_p = g_MRWPlan[MRW_id];
      for( int i = MRW_p->plan.size() - 1; i >= 0; i--){
        p.push_back(MRW_p->plan[i]);
      }
      cout << "[LAMA] trace a path (" << MRW_p->plan.size() << " steps) expanded by MRW" << MRW_id << endl;
      parent_s = (State *)MRW_p->init_state;
    }
    if( parent_s == current_s)
      break;
  }
	reverse(p.begin(), p.end());
  /*
  //check solution plan 
#ifdef VERBOSE
  cout << "[LAMA] validate the plan ...";
#endif
  State ss = *g_initial_state;
  for( int i = 0; i < p.size(); i++){
    if(!p[i]->is_applicable(ss)){
      cout << "[LAMA] error in trace solution path!"<< endl;
      exit(0);
    }else
      ss = State(ss, *p[i]);
  }
  for (int i = 0; i < g_goal.size(); i++){
    if( ss[g_goal[i].first] != g_goal[i].second ){
      cout << "[LAMA] error in trace solution path!"<< endl;
      exit(0);
    }
  }
  */
}

