/*********************************************************************
 * Author: Silvia Richter (silvia.richter@nicta.coma.au)
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

#include "wa_star_search.h"
#include "globals.h"
#include "heuristic.h"
#include "successor_generator.h"
#include "operator.h"
#include "ff_heuristic.h"
#include "landmarks_count_heuristic.h"

#include <climits>
#include <cassert>
using namespace std;


WAStarSearchEngine::WAStarSearchEngine(int w, int b)
    : BestFirstSearchEngine() {
    weight = w;
    bound = b;
}

void WAStarSearchEngine::debug_print_partial_plan(const State& state) {
    if(current_operator != 0) {
	Plan plan;
	closed_list.trace_path(state, plan);
	for(int i = 0; i < plan.size(); i++) {
	    cout << plan[i]->get_name() << " (" << plan[i]->get_cost() << ")" << endl;
	}
    }
}


void WAStarSearchEngine::add_heuristic(Heuristic *heuristic,
		bool use_estimates, bool use_preferred_operators) {
	assert(use_estimates || use_preferred_operators);
	heuristics.push_back(heuristic);
	best_heuristic_values.push_back(-1);
  open_lists.push_back(OpenListInfo(heuristic, false));
  if(use_preferred_operators)
    open_lists.push_back(OpenListInfo(heuristic, true));
  
  if (use_preferred_operators)
    preferred_operator_heuristics.push_back(heuristic);
}


void WAStarSearchEngine::initialize() {
    cout << "Conducting weighted A* search, weight is " << weight << ", bound is " 
	 << bound <<  "." << endl;
    assert(!open_lists.empty());
    total_min_heu_val = INT_MAX;
}

int WAStarSearchEngine::step() {
    // Invariants:
    // - current_state is the next state for which we want to compute the heuristic.
    // - current_predecessor is a permanent pointer to the predecessor of that state.
    // - current_operator is the operator which leads to current_state from predecessor.

  if (g_found_solution) {
    cout << "[LAMA] Respect solutions from other threads\n";
    return SOLVED;
  }


    // Evaluate only if g-cost of state is lower than bound
    if(bound != -1 && current_state.get_g_value() >= bound) { 
      return fetch_next_state();
    }

    bool evaluate = false;
    const State *parent_ptr = 0;
    if(!closed_list.contains(current_state)) {  
      parent_ptr = closed_list.insert(current_state, current_predecessor, current_operator);
      evaluate = true;
    } else {
      parent_ptr = closed_list.find(current_state);
      if(current_state.get_g_value() < parent_ptr->get_g_value()) {
	    // Re-evaluate, since we have found a shorter path to this state.
	    // We need a const_cast here, as we have to modify parent, but the 
            // STL Map underlying closed_list returns a const_iterator. However, cast
            // is safe as modification of parent does not effect its position in closed_list.
	      State *modifiable_parent_ptr = const_cast<State*> (parent_ptr);
	      // Change g-value and reached landmarks in state
	      modifiable_parent_ptr->change_ancestor(*current_predecessor, *current_operator);
	      //modifiable_parent_ptr->set_MRW_id(-1);
	      // Record new predecessor for state in closed_list
	      closed_list.update(current_state, current_predecessor, current_operator); 
	      evaluate = true;
      }
    }
    if(evaluate) {
      if(g_lm_heur != NULL)
        g_lm_heur->set_recompute_heuristic(current_state);
      if(g_ff_heur != NULL)
        g_ff_heur->set_recompute_heuristic(); 
      for(int i = 0; i < heuristics.size(); i++) 
        heuristics[i]->evaluate(current_state);   
      
      if(!is_dead_end()) {
        if(check_goal(current_state))
          return SOLVED;
        generate_successors(parent_ptr);
        if(check_progress( (State*) parent_ptr )) {
          report_progress();
          reward_progress();
        }
      }
    }
    return fetch_next_state();
}

void WAStarSearchEngine::generate_successors(const State *parent_ptr) {
  vector<const Operator *> all_operators;
  g_successor_generator->generate_applicable_ops(*parent_ptr, all_operators);
  vector<const Operator *> preferred_operators;
  for(int i = 0; i < preferred_operator_heuristics.size(); i++) {
    Heuristic *heur = preferred_operator_heuristics[i];
    if(!heur->is_dead_end())
      heur->get_preferred_operators(preferred_operators);
  }
  
  //Lu store ops' index in bfd_open queue
	for (int j = 0; j < all_operators.size(); j++) {
    assert(all_operators[j]->is_applicable(*parent_ptr)); //Add by Lu
    ops_index[all_operators[j]] = bfs_open.size();
    bfs_open.push_back(OpenListEntry(parent_ptr, all_operators[j]));
  }

  for(int i = 0; i < open_lists.size(); i++) {
    Heuristic *heur = open_lists[i].heuristic;
    if(!heur->is_dead_end()) {
      int parent_h = heur->get_heuristic();
      int parent_g = parent_ptr->get_g_value();
      int parent_f = weight * parent_h + parent_g;
      OpenList<int> &open = open_lists[i].open;
      vector<const Operator *> &ops =	open_lists[i].only_preferred_operators ? preferred_operators : all_operators;
      for(int j = 0; j < ops.size(); j++) {
        int h = parent_f + ops[j]->get_cost();
        int tie_braker = parent_g + ops[j]->get_cost();
        assert(ops[j]->is_applicable(*parent_ptr));
        int id = ops_index[ops[j]];
				//open.insert(make_pair(h, tie_braker), OpenListEntry(parent_ptr, ops[j], h));
				open.insert(make_pair(h, tie_braker), id);
        //open.insert(make_pair(h, tie_braker), OpenListEntry(parent_ptr, ops[j], parent_h));
      }
    }
  }
  generated_states += all_operators.size();
}

bool WAStarSearchEngine::check_progress(const State* s) {
	bool progress = false;
	for (int i = 0; i < heuristics.size(); i++) {
		if (heuristics[i]->is_dead_end())
			continue;
		int h = heuristics[i]->get_heuristic();
		int &best_h = best_heuristic_values[i];
		if (best_h == -1 || h < best_h) {
			best_h = h;
			progress = true;
			milestone = 0;
    }
 	}
  return progress;
}


/*
#ifdef STOC
void* conduct_mrw_search(void* input);
//add by Lu begin
bool WAStarSearchEngine::call_mrw(const State* s) {
  //assert(g_stoc_instances == 1);
  bool progress = false;
  int num_MRW_plans = g_MRWPlan.size();
	int ret;
  g_mrw_stop = 2;
  cout << "[LAMA] call MRW to expand neighborhood states" << endl;
	for (int i = 0; i < g_stoc_instances; i++) {
    g_engine_messages[i].init_state = s;
    g_engine_messages[i].heu_bound = total_min_heu_val;
		ret = pthread_create(&g_threads[i], NULL, conduct_mrw_search,	(void*) &g_engine_messages[i]);
		if (ret)
			cout << "[ERROR] Error code is " << i << " in creating MRW threads" << ret << endl;
	}

	for (int i = 0; i < g_stoc_instances; i++) {
		pthread_join(g_threads[i], NULL);
	}

  //expand the paths search by MRWs
  while(num_MRW_plans < g_MRWPlan.size()) { 
    //MRW has expanded some paths
    MRWPlan const * p = g_MRWPlan[num_MRW_plans];
    assert(p->plan.size() > 0);
    State t_current_state = State(*p->init_state, *(p->plan[0]));
    State const * p_current_predecessor = p->init_state;
    Operator const * p_current_operator = p->plan[0];
    int path_cost = p->plan[0]->get_cost();
    for (int i = 1; i < p->plan.size(); ++i) {
		  t_current_state = State(t_current_state, *(p->plan[i]));
      p_current_operator = p->plan[i];
      path_cost += p->plan[i]->get_cost() - 1; //when using metric, action cost has been increased by 1
	  }
    t_current_state.set_MRW_id(num_MRW_plans);

    if(bound != -1 && t_current_state.get_g_value() >= bound) { 
      cout << "[LAMA] ignore a path expanding by MRW [" << t_current_state.get_g_value() << "/" << bound << "]"<< endl;
      num_MRW_plans++;
      continue;
    }

    bool evaluate = false;
    const State *parent_ptr = 0;
    if(!closed_list.contains(t_current_state)) {  
      parent_ptr = closed_list.insert(t_current_state, p_current_predecessor, p_current_operator);
      evaluate = true;
    } else {
      parent_ptr = closed_list.find(current_state);
      if(t_current_state.get_g_value() < parent_ptr->get_g_value()) {
	    // Re-evaluate, since we have found a shorter path to this state.
	    // We need a const_cast here, as we have to modify parent, but the 
            // STL Map underlying closed_list returns a const_iterator. However, cast
            // is safe as modification of parent does not effect its position in closed_list.
	      State *modifiable_parent_ptr = const_cast<State*> (parent_ptr);
	      // Change g-value and reached landmarks in state
	      modifiable_parent_ptr->change_path_ancestor(*p_current_predecessor, *p_current_operator, path_cost);
	      modifiable_parent_ptr->set_MRW_id(num_MRW_plans);
	      // Record new predecessor for state in closed_list
	      closed_list.update(t_current_state, p_current_predecessor, p_current_operator); 
	      evaluate = true;
      }
    }
    if(evaluate) {
      cout <<"[LAMA] insert a valuable path (" << p->plan.size() <<" steps)" << endl;
      progress = true;
      if(g_lm_heur != NULL)
        g_lm_heur->set_recompute_heuristic(t_current_state);
      if(g_ff_heur != NULL)
        g_ff_heur->set_recompute_heuristic(); 
      for(int i = 0; i < heuristics.size(); i++) 
        heuristics[i]->evaluate(t_current_state);   
      
      if(!is_dead_end()) {
        if(check_goal(t_current_state)){
          g_found_solution = true;
          return progress;
        }
        generate_successors(parent_ptr);
      }
    }
    num_MRW_plans++;
  }
  return progress;
}
//add by Lu end
#endif
*/
