/*********************************************************************
 * Author: Malte Helmert (helmert@informatik.uni-freiburg.de)
 * (C) Copyright 2003-2004 Malte Helmert
 * Modified by: Silvia Richter (silvia.richter@nicta.com.au),
 *              Matthias Westphal (westpham@informatik.uni-freiburg.de)             
 * (C) Copyright 2008 NICTA and Matthias Westphal
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

#include "best_first_search.h"
#include "wa_star_search.h"
#include "ff_heuristic.h"
#include "globals.h"
#include "operator.h"
#include "landmarks_graph.h"
#include "landmarks_graph_rpg_sasp.h"
#include "landmarks_count_heuristic.h"
#include "state.h"

#include <cassert>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <sys/times.h>
#include <climits>
#include "switches.h"

#ifdef STOC
#include "mrw.h"
//#include <pthread.h>
#endif

using namespace std;

int save_plan(const vector<const Operator *> &plan, const string& filename,
		int iteration);

int dump_plan(const vector<const Operator *> &plan);

#ifdef FINDPLATEAU
int dump_trajectory( vector< State* > &trajectory);
#endif

BestFirstSearchEngine* initialize_lama_search(bool, bool, bool, bool);

#ifdef STOC
void initialize_mrw_search();
void* conduct_mrw_search(MRWMessage* m);
void* conduct_lama_search();
bool conduct_lama_wa_search();
#endif

void print_heuristics_used(bool ff_heuristic, bool ff_preferred_operators,
		bool landmarks_heuristic, bool landmarks_heuristic_preferred_operators);
//Lu
bool ff_heuristic = false, ff_preferred_operators = false;
bool landmarks_heuristic = false, landmarks_preferred_operators = false;
bool iterative_search = false;
struct tms start, search_start, search_end;
typedef enum {
  wa_star, bfs
} Search_type; 

Search_type search_type = bfs;

string plan_filename = "sas_plan";
string input_file = "output"; // the output from preprocessing
string input_group = "all.group";

int main(int argc, const char **argv) {

	srand(2011);
  //Lu for debug
  //g_use_multi_ff = false;
	bool poly_time_method = false;
	bool reasonable_orders = true;
#ifdef STOC
	bool stochastic_search = false;
  g_stoc_instances = 8;
#endif
  g_ff_heur = NULL;
  g_lm_heur = NULL;

	times(&start);
	if (argc < 2 || argc > 5) {
		std::cout << "Usage: \"search options [outputfile]\"\n";
	} else {
		for (const char *c = argv[1]; *c != 0; c++) {
			if (*c == 'f') {
				ff_heuristic = true;
			} else if (*c == 'F') {
				ff_preferred_operators = true;
			} else if (*c == 'l') {
				landmarks_heuristic = true;
			} else if (*c == 'L') {
				landmarks_preferred_operators = true;
			} else if (*c == 'w') {
				search_type = wa_star;
			} else if (*c == 'i') {
				iterative_search = true;
			}
#ifdef STOC
			else if (*c == 's') {
				stochastic_search = true;
        g_stoc_instances = 0;
				c++;
        while(*c >= '0' && *c <= '9'){
          g_stoc_instances = g_stoc_instances *10 + (*c - '0');
          c++;
        }
        c--;
				cout << "[INFO] Use " << g_stoc_instances	<< " instances of stochastic searches\n";
			}
#endif
			else {
				cerr << "Unknown option: " << *c << endl;
				return 1;
			}
		}
		if (argc == 5) {
			input_file = argv[2];
			input_group = argv[3];
      plan_filename = argv[4];
		}
	}
	if (!ff_heuristic && !landmarks_heuristic) {
		cerr << "Error: you must select at least one heuristic!" << endl
				<< "If you are unsure, choose options \"fFlL\"." << endl;
		return 2;
	}

	ifstream fin;
	fin.open(input_file.c_str(), ifstream::in);

	ifstream group_in;
	group_in.open(input_group.c_str(), ifstream::in);

	fin >> poly_time_method;
	if (poly_time_method) {
		cout << "[INFO] Poly-time method not implemented in this branch."
				<< endl;
		cout << "[INFO] Starting normal solver." << endl;
	}

#ifdef STOC
  //Lu make sure that LAMA has to use FF heuristic
	if (g_stoc_instances > 0) {
    landmarks_heuristic = true;
    landmarks_preferred_operators = true;
    ff_heuristic = true;
    ff_preferred_operators = true;
  }
	g_ff_heur_for_stoc = (FFHeuristic**) malloc(g_stoc_instances	* sizeof(FFHeuristic*));
	//comment by Lu
	//g_ff_heur_for_stoc = (MRWFFHeuristic**) malloc(g_stoc_instances	* sizeof(MRWFFHeuristic*));
  /*g_state_candidates = (State**) malloc(g_stoc_instances * g_multiplier * sizeof(State*));
	g_state_candidates_priority = (int*) malloc(g_stoc_instances * g_multiplier	* sizeof(int));
	g_state_value = (int*) malloc(g_stoc_instances * g_multiplier * sizeof(int));
	for (int i = 0; i < g_stoc_instances * g_multiplier; i++) {
		g_state_candidates_priority[i] = -1;
		g_state_value[i] = -1;
	}*/
#endif

	/* Read input and generate landmarks */
	bool generate_landmarks = false;
	g_lgraph = NULL;
	g_lm_heur = NULL;

	if (landmarks_heuristic || landmarks_preferred_operators)
		generate_landmarks = true;
	read_everything(fin, generate_landmarks, reasonable_orders, group_in);

	if (generate_landmarks && g_lgraph->number_of_landmarks() == 0) {
		if (landmarks_heuristic) {
			landmarks_heuristic = false;
		}
		if (!ff_heuristic) {
			ff_heuristic = true;
			ff_preferred_operators = true;
		}
	}

	bool solution_found = false;
	//g_state_candidates[0] = g_initial_state;
	//g_state_candidates_priority[0] = 4000; // initial priority for initial state
  
  //delete by Lu begin
	/* Conducting parallel searches */
  /*
	pthread_t* threads = new pthread_t[g_stoc_instances + 1];
	MRW ** stochastic_engines = new MRW*[g_stoc_instances];
	MRWMessage * engine_messages = new MRWMessage[g_stoc_instances];
	int ret;
	for (int i = 0; i < g_stoc_instances; i++) {
		engine_messages[i].engine = stochastic_engines[i];
		engine_messages[i].heuristic = g_ff_heur_for_stoc[i];
		engine_messages[i].id = i;
		engine_messages[i].maxsteps = 6 + i;
		engine_messages[i].numwalk = 2000 + i;
		ret = pthread_create(&threads[i], NULL, conduct_mrw_search,
				(void*) &engine_messages[i]);
		if (ret)
			cout << "[ERROR] Error code is " << i << " in creating MRW threads"
					<< ret << endl;
	}
  */
	/* Thread for LAMA */
	/*
  ret = pthread_create(&threads[g_stoc_instances], NULL, conduct_lama_search,
			(void*) 0);
	if (ret) {
		cout << "[ERROR] Error code is " << ret << " in creating LAMA thread."
				<< endl;
	}

	for (int i = 0; i < g_stoc_instances; i++) {
		pthread_join(threads[i], NULL);
	}
	pthread_join(threads[g_stoc_instances], NULL);
  */
  //delete by Lu end
  
  //initialize messages for MRW search
  initialize_mrw_search();

  //conduct_lama_search();
  solution_found = conduct_lama_wa_search();
	
  cout << "[CACHE-MRW] Cache hits: " << g_mrw_cache_hit << endl;
	cout << "[CACHE-MRW] Cache misses: " << g_mrw_cache_miss << endl;
	cout << "[CACHE-expanded] Cache hits: " << g_expanded_hits << endl;

	return solution_found ? 0 : 1;
}

//Add by Lu
void initialize_mrw_search(){
  //Add by Lu initialize multi-threads MRW
  //g_threads = new pthread_t[g_stoc_instances];
	g_stochastic_engines = new MRW*[g_stoc_instances];
	g_engine_messages = new MRWMessage[g_stoc_instances];

	for (int i = 0; i < g_stoc_instances; i++) {
		g_engine_messages[i].engine = new MRW(g_initial_state);
		
    FFHeuristic * ff = new FFHeuristic(false, g_use_cache, 1);
		g_engine_messages[i].engine->add_heuristic(ff, true, false);
		g_engine_messages[i].engine->set_id(i);
  }
}

void* conduct_mrw_search(MRWMessage* m) {
	MRW* mrwengine = m->engine;

	mrwengine->set_init_state(m->init_state);
	//mrwengine->set_heu_bound(m->heu_bound);
  mrwengine->set_step_cost_bound(m->step_cost);
	mrwengine->search();
	//cout << "[INFO] MRW" << m->id << ": Search is over.\n";
	return NULL;
}

bool conduct_lama_wa_search() {
  int iteration_no = 0;
  bool solution_found = false;
  int wa_star_weights[] = {10, 5, 3, 2, 1, -1};
  int wastar_bound = -1;
  int wastar_weight = wa_star_weights[0];
  bool reducing_weight = true;
  bool use_metric = g_use_metric;

  do{
    g_found_solution = false;
    iteration_no++;
    cout << "[Info] Search iteration " << iteration_no << endl;
    if(reducing_weight && wa_star_weights[iteration_no - 1] != -1)
      wastar_weight = wa_star_weights[iteration_no - 1];
    else {
      cout << "[Info] No more new weight, weight is " << wastar_weight << endl;
      reducing_weight = false;
    }
	  // Initialize search engine and heuristics (this is cheap and we want to vary search type
	  // and heuristics, so we initialize freshly in each iteration)
	  BestFirstSearchEngine* engine; 
	  if(search_type == wa_star){
		// Parameters of WAStar are 1) weight for heuristic, 2) upper bound on solution
		// cost (this cuts of search branches if the cost of a node exceeds the bound), 
		// use -1 for none.
      //Lu
      g_stoc_instances = 0;
      g_use_metric = use_metric;
	    engine = new WAStarSearchEngine(wastar_weight, wastar_bound);  
    }
	  else{
	    engine = new BestFirstSearchEngine;
    }

	  print_heuristics_used(ff_heuristic, ff_preferred_operators, landmarks_heuristic, landmarks_preferred_operators);
    if(landmarks_heuristic || landmarks_preferred_operators) {
      if(landmarks_preferred_operators)
        if(!g_ff_heur)
          g_ff_heur = new FFHeuristic(g_use_metric, false, 0);
      
      g_lm_heur = new LandmarksCountHeuristic(*g_lgraph, *engine, landmarks_preferred_operators, g_ff_heur, g_use_metric);
      engine->add_heuristic(g_lm_heur, landmarks_heuristic,
          landmarks_preferred_operators);
    }
    if(ff_heuristic || ff_preferred_operators) {
      if(!g_ff_heur)
        g_ff_heur = new FFHeuristic(g_use_metric, false, 0);
      engine->add_heuristic(g_ff_heur, ff_heuristic, ff_preferred_operators);
    } 

	  // Search
	  times(&search_start);
    engine->search();
    times(&search_end);
	  int plan_cost = INT_MAX;
	  if(engine->found_solution()){
      g_use_metric = use_metric;
	    plan_cost = save_plan(engine->get_plan(), plan_filename, iteration_no);
    }

	  engine->statistics();

	  int search_ms = (search_end.tms_utime - search_start.tms_utime) * 10;
	  cout << "Search time: " << search_ms / 1000.0 << " seconds" << endl;
	  int total_ms = (search_end.tms_utime - start.tms_utime) * 10;
	  cout << "Total time: " << total_ms / 1000.0 << " seconds" << endl;
	  solution_found |= engine->found_solution();

	  if(!engine->found_solution())
	    iterative_search = false;

	  // Set new parameters for next search
	  search_type = wa_star;
	  wastar_bound = plan_cost;

	  if(wastar_weight <= 2) { // make search less greedy
	    ff_preferred_operators = false;
	    landmarks_preferred_operators = false;
	  }

	  // If the heuristic weight was already 0, we can only search for better solutions
	  // by decreasing the bound (note: this could be improved by making WA* expand 
	  // all fringe states, but seems to have little importance).
	  if(wastar_weight == 0) {
	    wastar_bound--;
	  }
  }while(iterative_search);

  return solution_found; 
}

void* conduct_lama_search() {
	BestFirstSearchEngine* engine = initialize_lama_search(true, true, true,
			true);
	cout << "[INFO] LAMA: Search starts.\n";
	engine->search();
  if (engine->found_solution() ) {	
    //cout << "[TRAJ] "; 
		//dump_trajectory(engine->get_trajectory());
		dump_plan(engine->get_plan());
	  save_plan(engine->get_plan(), plan_filename, 0);
	}
	cout << "[INFO] LAMA: Search is over.\n";
	return NULL;
}

BestFirstSearchEngine* initialize_lama_search(bool ff_heuristic,
		bool ff_preferred_operators, bool landmarks_heuristic,
		bool landmarks_preferred_operators) {
	int iteration_no = 1;

	int wa_star_weights[] = { 10, 5, 3, 2, 1, -1 };

	g_ff_heur = NULL;
	int wastar_weight = wa_star_weights[0];
	bool reducing_weight = true;

	// cout << "Search iteration " << iteration_no << endl;
	if (reducing_weight && wa_star_weights[iteration_no - 1] != -1)
		wastar_weight = wa_star_weights[iteration_no - 1];
	else {
		cout << "No more new weight, weight is " << wastar_weight << endl;
		reducing_weight = false;
	}
	// Initialize search engine and heuristics (this is cheap and we want to vary search type
	// and heuristics, so we initialize freshly in each iteration)
	BestFirstSearchEngine* engine;
	engine = new BestFirstSearchEngine;

	print_heuristics_used(ff_heuristic, ff_preferred_operators, landmarks_heuristic, landmarks_preferred_operators);
	if (landmarks_heuristic || landmarks_preferred_operators) {
		if (landmarks_preferred_operators)
			if (!g_ff_heur)
				g_ff_heur = new FFHeuristic(g_use_metric, g_use_cache, 0);
		g_lm_heur = new LandmarksCountHeuristic(*g_lgraph, *engine,
				landmarks_preferred_operators, g_ff_heur, g_use_metric);
		engine->add_heuristic(g_lm_heur, landmarks_heuristic,
				landmarks_preferred_operators);
	}
	if (ff_heuristic || ff_preferred_operators) {
		if (!g_ff_heur)
			g_ff_heur = new FFHeuristic(g_use_metric, g_use_cache, 0);
		engine->add_heuristic(g_ff_heur, ff_heuristic, ff_preferred_operators);
	}
	return engine;
}

int dump_plan(const vector<const Operator *> &plan) {
	int plan_cost = 0;
	for (int i = 0; i < plan.size(); i++) {
		int action_cost = plan[i]->get_cost();
		if (g_use_metric)
			action_cost--;
		plan_cost += action_cost;
		if (!g_use_metric)
			cout << plan[i]->get_name() << endl;
		else
			cout << plan[i]->get_name() << " (" << action_cost << ")" << endl;
	}
	if (!g_use_metric)
		cout << "Plan length: " << plan.size() << " step(s)." << endl;
	else
		cout << "Plan length: " << plan.size() << " step(s), cost: "
				<< plan_cost << "." << endl;
	return plan_cost;
}

int dump_trajectory( vector< State* > &trajectory) {
	for (int i = 0; i < trajectory.size(); i++) {
		cout << trajectory[i]->my_value << "\t";
	}
	cout << endl;
	return 0;
}
int save_plan(const vector<const Operator *> &plan, const string& filename,
		int iteration) {
	ofstream outfile;
	int plan_cost = 0;
	bool separate_outfiles = true; // IPC conditions, change to false for a single outfile.
	if (separate_outfiles) {
		// Write a separat output file for each plan found by iterative search
		stringstream it_no;
		it_no << iteration;
		outfile.open((filename + "." + it_no.str()).c_str(), ios::out);
	} else {
		// Write newest plan always to same output file
		outfile.open(filename.c_str(), ios::out);
	}
#ifdef VERBOSE
  cout << "=========================================================" << endl;
#endif
	for (int i = 0; i < plan.size(); i++) {
		int action_cost = plan[i]->get_cost();
		if (g_use_metric)
			action_cost--;
		plan_cost += action_cost;
#ifdef VERBOSE
		if (!g_use_metric)
			cout << plan[i]->get_name() << endl;
		else
			cout << plan[i]->get_name() << " (" << action_cost << ")" << endl;
#endif
		outfile << "(" << plan[i]->get_name() << ")" << endl;
	}
	outfile.close();
#ifdef VERBOSE
  cout << "=========================================================" << endl;
#endif
	if (!g_use_metric)
		cout << "Plan length: " << plan.size() << " step(s)." << endl;
	else
		cout << "Plan length: " << plan.size() << " step(s), cost: "
				<< plan_cost << "." << endl;
	return plan_cost;
}

void print_heuristics_used(bool ff_heuristic, bool ff_preferred_operators,
		bool landmarks_heuristic, bool landmarks_preferred_operators) {
	cout << "[Info] Using the following heuristic(s):" << endl;
	if (ff_heuristic) {
		cout << "       FF heuristic ";
		if (ff_preferred_operators)
			cout << "with preferred operators";
		cout << endl;
	}
	if (landmarks_heuristic) {
		cout << "       Landmark heuristic ";
		if (landmarks_preferred_operators)
			cout << "with preferred operators";
		cout << endl;
	}
}
