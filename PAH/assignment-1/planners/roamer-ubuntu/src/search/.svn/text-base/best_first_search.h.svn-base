/*********************************************************************
 * Author: Malte Helmert (helmert@informatik.uni-freiburg.de)
 * (C) Copyright 2003-2004 Malte Helmert
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

#ifndef BEST_FIRST_SEARCH_H
#define BEST_FIRST_SEARCH_H

#include <vector>

#include "closed_list.h"
#include "open_list.h"
#include "search_engine.h"
#include "state.h"

class Heuristic;
class Operator;

//typedef pair<const State *, const Operator *> OpenListEntry;

struct OpenListEntry {
	OpenListEntry(const State *parent, const Operator *op );//, int parent_heur);
	const State *parent;
	const Operator *op;
	//int parent_heur;
  bool expanded;
};

struct OpenListInfo {
	OpenListInfo(Heuristic *heur, bool only_pref);
	Heuristic *heuristic;
	bool only_preferred_operators;
	//OpenList<OpenListEntry> open;
	OpenList<int> open; //Lu store the id in bfs_open queue
	int priority; // low value indicates high priority
};

class BestFirstSearchEngine: public SearchEngine {
protected:
	std::vector<Heuristic *> heuristics;
	std::vector<Heuristic *> preferred_operator_heuristics;
	std::vector<OpenListInfo> open_lists;
  std::vector<OpenListEntry> bfs_open;
  std::map<const Operator*, int> ops_index;
	ClosedList<State, const Operator *> closed_list;

	std::vector<int> best_heuristic_values;
	int generated_states;

	State current_state;
	const State *current_predecessor;
	const Operator *current_operator;

	bool is_dead_end();
	bool check_goal(State &);
  virtual bool call_mrw(const State*);
	bool check_progress(const State*);
	void report_progress();
	void reward_progress();
	void generate_successors(const State *parent_ptr);
	virtual int fetch_next_state();
	OpenListInfo *select_open_queue();

	virtual void initialize();
	virtual int step();

	int milestone;
	int current_heur;
  int total_min_heu_val;
  int total_min_step_cost;
	int plateau;

public:
	BestFirstSearchEngine();
	~BestFirstSearchEngine();
	virtual void add_heuristic(Heuristic *heuristic, bool use_estimates, bool use_preferred_operators);
	virtual void statistics() const;
  void trace_path(State & s, Plan & p);

	inline const Operator *get_current_operator() const {
		// Used by LandmarksCountHeuristic
		return current_operator;
	}
};

#endif
