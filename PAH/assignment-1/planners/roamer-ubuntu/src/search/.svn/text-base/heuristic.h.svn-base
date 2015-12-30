/*********************************************************************
 * Author: Malte Helmert (helmert@informatik.uni-freiburg.de)
 * (C) Copyright 2003-2004 Malte Helmert
 * Modified by: Matthias Westphal (westpham@informatik.uni-freiburg.de)
 * (C) Copyright 2008 Matthias Westphal
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

#ifndef HEURISTIC_H
#define HEURISTIC_H

#include <map>
#include <vector>
#include "switches.h"
#include <iostream>

class Operator;
class State;

#ifdef CACHE

struct EvaluationInfo {
	EvaluationInfo() {
		heuristic = -2;
	}
	EvaluationInfo(int heur) :
		heuristic(heur) {
	}
	int heuristic;
};

#else

struct EvaluationInfo {
	EvaluationInfo() {
		heuristic = -2;
	}
	EvaluationInfo(int heur, const std::vector<const Operator *> &pref) :
		heuristic(heur), preferred_operators(pref) {
	}
	int heuristic;
	std::vector<const Operator *> preferred_operators;
};
#endif

typedef enum{
  FF, LAMA
}Heuristic_type;

enum {
  DEAD_END = -1
};

class Heuristic {

	enum {
		INVALID = -2
	};
  int heuristic;
	std::vector<const Operator *> preferred_operators;
protected:
	virtual int compute_heuristic(const State &state) = 0;
	void set_preferred(const Operator *op);
public:
  int step_cost;
	bool use_cache;
  bool use_metric;
  virtual void set_recompute_heuristic() { };
	Heuristic(bool use_metric = false, bool use_cache = false, int is_mrw = 0, Heuristic_type type = LAMA);	// By Default, it is not MRW
	// Becareful here because lama might just use ff's cache????
	virtual ~Heuristic();

	void evaluate(const State &state);
	bool is_dead_end();
	int get_heuristic();
	int get_step_cost();
  inline Heuristic_type get_h_type() {return h_type;}
	void get_preferred_operators(std::vector<const Operator *> &result);
	virtual bool dead_ends_are_reliable() {
		return true;
	}
	bool read_only; 
	int is_mrw;			// two hacks
  Heuristic_type h_type; //Lu
};

#endif
