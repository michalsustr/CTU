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

#ifndef GLOBALS_H
#define GLOBALS_H

#include <iostream>
#include <string>
#include <vector>
#include "switches.h"
#include "state.h"
#include "closed_list.h"
#include "heuristic.h"
//#include <pthread.h>

using namespace std;

class AxiomEvaluator;
class Cache;
class CausalGraph;
class DomainTransitionGraph;
class Operator;
class Axiom;
class State;
class SuccessorGenerator;
class FFHeuristic;
class LandmarksCountHeuristic;
class LandmarksGraph;

//Add by Lu start
#ifdef STOC
class MRWFFHeuristic;
class MRW;
class State;
typedef struct {
	MRW* engine;
	FFHeuristic* heuristic;
	//MRWFFHeuristic* heuristic;
  const State* init_state;
  int heu_bound;
  int step_cost;
	int id;
	int numwalk;
	int maxsteps;
} MRWMessage;

typedef struct _MRWPlan{
  State const * init_state;
  vector <Operator const *> plan;
}MRWPlan;

#define MAX_PLATEAU_SIZE 40000
extern int PLATEAU_SIZE;
extern int g_mrw_stop;
extern bool g_find_plateau;
extern vector<MRWPlan const *> g_MRWPlan;
//extern pthread_t* g_threads;
extern MRW ** g_stochastic_engines;
extern MRWMessage * g_engine_messages; 
extern bool g_use_multi_ff;
#endif 
//Add by Lu end

void read_everything(istream &in, bool generate_landmarks,
		bool reasonable_orders, istream& groupinb);
void dump_everything();

void check_magic(istream &in, string magic);

struct hash_operator_ptr {
	size_t operator()(const Operator *key) const {
		return reinterpret_cast<unsigned long> (key);
	}
};

extern bool g_use_metric;
extern vector<string> g_variable_name;
extern vector<int> g_variable_domain;
extern vector<int> g_axiom_layers;
extern vector<int> g_default_axiom_values;

extern State *g_initial_state;
extern vector<pair<int, int> > g_goal;
extern vector<Operator> g_operators;
extern vector<Operator> g_axioms;
extern AxiomEvaluator *g_axiom_evaluator;
extern SuccessorGenerator *g_successor_generator;
extern vector<DomainTransitionGraph *> g_transition_graphs;
extern CausalGraph *g_causal_graph;
extern Cache *g_cache;
extern int g_cache_hits, g_cache_misses;
extern int g_expanded_hits;

extern FFHeuristic *g_ff_heur;
extern LandmarksCountHeuristic *g_lm_heur;
extern LandmarksGraph *g_lgraph;

#ifdef STOC
bool check_goal(State* s);
int fetch_next_state_index();
int insert_state(State* s, int p, int h);
void update_state(int flag, int p);
extern int g_stoc_instances;
extern FFHeuristic** g_ff_heur_for_stoc;
//extern MRWFFHeuristic** g_ff_heur_for_stoc;
extern bool g_found_solution;
extern ClosedList<State, const Operator *> g_pseudo_closed_list;
extern State** g_state_candidates;
extern int* g_state_candidates_priority;
extern int* g_state_value;
extern int g_multiplier;

#ifndef CACHE
extern std::map<State, EvaluationInfo> g_state_cache;
#else
extern std::map<State, int> g_state_cache;
#endif
//extern pthread_mutex_t spin_lock;
extern int g_cached_heuristics;
#ifdef CACHE
extern bool g_use_cache;
extern int g_mrw_cache_hit;
extern int g_mrw_cache_miss;
extern int g_lama_cache_hit;
extern int g_last_open;
#endif

class WalkInfo {
public:
	int branching;
	int length_offset;
	vector<const Operator*> path;
	bool goal_visited;
	int value;
};
#endif

#endif
