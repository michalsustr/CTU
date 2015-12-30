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

#include "globals.h"

#include <cstdlib>
#include <iostream>
#include <string>
#include <vector>
#include <limits.h>
using namespace std;

#include "axioms.h"
#include "domain_transition_graph.h"
#include "operator.h"
#include "state.h"
#include "successor_generator.h"
#include "landmarks_graph.h"
#include "landmarks_graph_rpg_sasp.h"
#include "ff_heuristic.h"
#include "closed_list.h"
#include "switches.h"

#ifdef STOC
#include "walker.h"
#endif

void check_magic(istream &in, string magic) {
	string word;
	in >> word;
	if (word != magic) {
		cout << "Failed to match magic word '" << magic << "'." << endl;
		cout << "Got '" << word << "'." << endl;
		exit(1);
	}
}

void read_metric(istream &in) {
	check_magic(in, "begin_metric");
	in >> g_use_metric;
	check_magic(in, "end_metric");
}

void read_variables(istream &in) {
	check_magic(in, "begin_variables");
	int count;
	in >> count;
	for (int i = 0; i < count; i++) {
		string name;
		in >> name;
		g_variable_name.push_back(name);
		int range;
		in >> range;
		g_variable_domain.push_back(range);
		int layer;
		in >> layer;
		g_axiom_layers.push_back(layer);
	}
	check_magic(in, "end_variables");
}

void read_goal(istream &in) {
	check_magic(in, "begin_goal");
	int count;
	in >> count;
	for (int i = 0; i < count; i++) {
		int var, val;
		in >> var >> val;
		g_goal.push_back(make_pair(var, val));
	}
	check_magic(in, "end_goal");
}

void dump_goal() {
	cout << "Goal Conditions:" << endl;
	for (int i = 0; i < g_goal.size(); i++)
		cout << "  " << g_variable_name[g_goal[i].first] << ": "
				<< g_goal[i].second << endl;
}

void read_operators(istream &in) {
	int count;
	in >> count;
	for (int i = 0; i < count; i++)
		g_operators.push_back(Operator(in, false));
}

void read_axioms(istream &in) {
	int count;
	in >> count;
	for (int i = 0; i < count; i++)
		g_axioms.push_back(Operator(in, true));

	g_axiom_evaluator = new AxiomEvaluator;
	g_axiom_evaluator->evaluate(*g_initial_state);
}

void build_landmarks_graph(bool reasonable_orders, istream& in) {
	g_lgraph = new LandmarksGraphNew();
	g_lgraph->read_external_inconsistencies(in);
	if (reasonable_orders) {
		g_lgraph->use_reasonable_orders();
	}
	g_lgraph->generate();
	/*
	 cout << "Generated " << g_lgraph->number_of_landmarks() << " landmarks, of which "
	 << g_lgraph->number_of_disj_landmarks() << " are disjunctive" << endl
	 << "          " << g_lgraph->number_of_edges() << " edges\n";
	 */
	//g_lgraph->dump();
}

void read_everything(istream &in, bool generate_landmarks,
		bool reasonable_orders, istream& group_in) {
	read_metric(in);
	read_variables(in);
	g_initial_state = new State(in);
	read_goal(in);
	read_operators(in);
	read_axioms(in);
	check_magic(in, "begin_SG");
	g_successor_generator = read_successor_generator(in);
	check_magic(in, "end_SG");
	DomainTransitionGraph::read_all(in);
	if (generate_landmarks) {
		if (!g_ff_heur){
			g_ff_heur = new FFHeuristic(g_use_metric, false, 0);
    }

		build_landmarks_graph(reasonable_orders, group_in);
	}
	g_initial_state->set_landmarks_for_initial_state();
}

void dump_everything() {
	cout << "Use metric? " << g_use_metric << endl;
	cout << "Variables (" << g_variable_name.size() << "):" << endl;
	for (int i = 0; i < g_variable_name.size(); i++)
		cout << "  " << g_variable_name[i] << " (range "
				<< g_variable_domain[i] << ")" << endl;
	cout << "Initial State:" << endl;
	g_initial_state->dump();
	dump_goal();
	cout << "Successor Generator:" << endl;
	g_successor_generator->dump();
	for (int i = 0; i < g_variable_domain.size(); i++)
		g_transition_graphs[i]->dump();
}

#ifdef STOC
bool check_goal(State* state) {
	for (int i = 0; i < g_goal.size(); ++i) {
		//if (s[g_goal[i].first] != g_goal[i].second)
		if (state->operator [](g_goal[i].first) != g_goal[i].second)
			return false;
	}
	return true;
}
#endif

bool g_use_metric;
vector<string> g_variable_name;
vector<int> g_variable_domain;
vector<int> g_axiom_layers;
vector<int> g_default_axiom_values;
State *g_initial_state;
vector<pair<int, int> > g_goal;
vector<Operator> g_operators;
vector<Operator> g_axioms;
AxiomEvaluator *g_axiom_evaluator;
SuccessorGenerator *g_successor_generator;
vector<DomainTransitionGraph *> g_transition_graphs;
CausalGraph *g_causal_graph;
Cache *g_cache;
int g_cache_hits = 0, g_cache_misses = 0;
int g_expanded_hits = 0;

FFHeuristic *g_ff_heur;
LandmarksCountHeuristic *g_lm_heur;
LandmarksGraph *g_lgraph;

#ifdef STOC
FFHeuristic **g_ff_heur_for_stoc;
//MRWFFHeuristic **g_ff_heur_for_stoc;
int PLATEAU_SIZE = 3000;
int g_stoc_instances = 4;
bool g_use_multi_ff = true;
bool g_found_solution = false;
ClosedList<State, const Operator *> g_pseudo_closed_list;

//Lu
#ifdef STOC
vector<const MRWPlan*> g_MRWPlan;
int g_mrw_stop;
bool g_find_plateau;
//pthread_t* g_threads;
MRW ** g_stochastic_engines;
MRWMessage * g_engine_messages; 
#endif


/* Operations and data structure for communicating between LAMA and STOC
 * later will change it to priority queue */

//Add by Xu begin
// TODO change it to priority queue if necessary
State** g_state_candidates;
int* g_state_candidates_priority;
int* g_state_value;
int g_multiplier = 10;

void dump_queue() {
    for (int i = 0; i < g_stoc_instances * g_multiplier; i++) {
	if ( g_state_candidates_priority[i] != -1) 
		cout << i << "\t";
    }
    cout << endl;
    for (int i = 0; i < g_stoc_instances * g_multiplier; i++) {
	if ( g_state_candidates_priority[i] != -1) 
		cout << g_state_candidates_priority[i] << "\t";
    }
    cout << endl;
}

/* Get the one with max priority */
int fetch_next_state_index() {
	int flag = 0;
	int max = INT_MIN;
	#ifdef VERBOSE
	dump_queue();
	#endif	

	for (int i = 0; i < g_stoc_instances * g_multiplier; i++) {
		if (g_state_candidates_priority[i] > max) {
			flag = i;
			max = g_state_candidates_priority[i];
		}
	}
	return flag;
}

/* Remove the one with minimal priority */
int insert_state(State* s, int p, int h) {
	int flag = 0;
	int min = INT_MAX;
	for (int i = 0; i < g_stoc_instances * g_multiplier; i++) {
		if (g_state_candidates_priority[i] < min) {
			flag = i;
			min = g_state_candidates_priority[i];
		}
	}
	g_state_candidates[flag] = s;
	g_state_candidates_priority[flag] = p;
	g_state_value[flag] = h;
	return flag;
}

void update_state(int flag, int p) {
	g_state_candidates_priority[flag] = p;
}

#ifndef CACHE
std::map<State, EvaluationInfo> g_state_cache;
#else
std::map<State, int> g_state_cache;
#endif

//pthread_mutex_t spin_lock;
int g_cached_heuristics = 0;
#ifdef CACHE
bool g_use_cache = true;
int g_mrw_cache_hit;
int g_mrw_cache_miss;
int g_lama_cache_hit;
int g_last_open; 

#else
bool g_use_cache = false;
#endif
//Add by Xu end

#endif

