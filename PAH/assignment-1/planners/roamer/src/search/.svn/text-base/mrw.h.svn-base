#ifndef MRW_H_
#define MRW_H_

#include <vector>

#include "state.h"
#include "heuristic.h"
#include "operator.h"
#include "search_engine.h"
#include "switches.h"
#include "globals.h"
#include <climits>

using namespace std;

enum restart_type {
    DEADEND, TOO_DEEP
}; 

class Parameters {
	// This class is a container for the parameters used in MRW

public:
	enum {
		PURE = 0, MDA = 1, MHA = 2
	};
	float extending_rate;
	float temperture;
	int num_walk;
	int length_walk;
	int type;

	float alpha;
	float extending_period;
	int length_jump;
	int max_steps;
	bool deepening;
  int steps_bound;

	Parameters() {}
	Parameters(float r, float temp, int n, int l, int t) :
		extending_rate(r), temperture(temp), num_walk(n), length_walk(l), type(t) {

		alpha = 0.9;
		extending_period = 0.15;
		length_jump = length_walk;
		max_steps = 7;
    steps_bound = max_steps * 10;
		deepening = true;
	}
	void set_numwalk(int w) {
		num_walk = w;
	}

	void set_step(int s) {
		max_steps = s;
	}
};

class MRW: public SearchEngine {
public:
	typedef vector<const Operator*> Path;

private:
	int initial_value;
	int current_value;
	int evaluated_states;
	int num_jumps;
	int avg_branching;
	float acceptable_progress;
	float failure_percentage;
	bool goal_visited;
	State current_state;
  State const * initial_state;
	Heuristic* heuristic;
	Path plan;
	int total_min;
	int current_min;
	Parameters params;

	int id;
  int heu_bound; // Add by Lu
  int step_cost_bound; // Add by Lu
  int num_step;
	int start_state_flag;

	void update_current_min(int h, Path& temp_path, Path& min_path);
	bool hill_climbing();
	void dump(int num_dead_ends, int period, int iteration);
	void update_acceptable_progress();
	void update_total_min();
	void check_fallback_strategies();
	State jump(State state, Path& path, int jump_length);
	#ifdef STOC
	State store_jump(State state, Path& path, int jump_length);
	#endif
	void restart(int i);

protected:
	virtual int step();
	virtual void initialize();

public:
	enum {
		A_LOT = INT_MAX
	};
	virtual void add_heuristic(Heuristic *heuristic, bool use_estimates, bool use_preferred_operators);
	void set_id(int i);
	void set_numwalk(int nw);
	void set_maxsteps(int ms);
#ifdef STOC
  void set_heu_bound(int heu); //Add by Lu
  void set_step_cost_bound(int heu); //Add by Lu
  void set_init_state(const State * sp);
  void store_plan(); //Lu
#endif
	WalkInfo *info;
	MRW(State const * sp);
	virtual ~MRW();
};

#endif /*MRW_H_*/
