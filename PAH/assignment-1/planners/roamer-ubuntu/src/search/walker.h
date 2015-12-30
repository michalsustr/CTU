#ifndef WALKER_H_
#define WALKER_H_

#include "vector"
#include "state.h"
#include "operator.h"
#include "heuristic.h"
#include "vector"	
#include "globals.h"
#include "mrw.h"

using namespace std;

// This class is designed to run random_walks. Based on the given parameters
// action selection can be biased towards some actions and away some other. 

class Walker {
	vector<double> Q;
	vector<int> num;
	int current_value;
	// static WalkInfo info;
	WalkInfo* info;
	// static Heuristic* heuristic;
	Heuristic* heuristic;

	const Operator* pure_successor(State & s, vector<const Operator *>& applicable_ops);
	const Operator* mha_successor(State & s, vector<const Operator *>& applicable_ops,
			Parameters& param);
	const Operator* mda_successor(State & s, vector<const Operator *>& applicable_ops,
			Parameters& param);

	double gibbs_func(double avg, float temperture);
	void update_mda_action_values();
	void update_mha_action_values(vector<const Operator *> &helpful_actions);

public:

	Walker(int current_value);
	// static void set_heuristic(Heuristic* h){heuristic = h;}
	// static WalkInfo get_info(){return info;}
	// static void init_info();
	void set_heuristic(Heuristic* h) {
		heuristic = h;
	}
	void init_info();
	void set_info(WalkInfo* info);
	void random_walk(State current_state, int length, Parameters& params);
	virtual ~Walker();
};
#endif /*WALKER_H_*/
