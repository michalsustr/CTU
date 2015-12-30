#include "walker.h"

#include "successor_generator.h"
#include "math.h"
#include <stdlib.h>
#include <time.h>

#define random( ) rand( )

Walker::Walker(int _current_value) :
	current_value(_current_value) {
	Q.resize(g_operators.size(), 0);
	num.resize(g_operators.size(), 0);
}
Walker::~Walker() {
}

void Walker::init_info() {
	info->branching = 0;
	info->goal_visited = false;
	info->length_offset = 0;
	info->path.clear();
	info->value = 0;
}

void Walker::set_info(WalkInfo* _info) {
	info = _info;
}

void Walker::random_walk(State initial_state, int length, Parameters& params) {
	init_info();

	State current_state = initial_state;
	for (int i = 0; i < length; ++i) {
		const Operator* op = 0;
		vector<const Operator *> applicable_ops;
		g_successor_generator->generate_applicable_ops(current_state,	applicable_ops);

		info->branching += applicable_ops.size();

		if (params.type == Parameters::PURE)
			op = pure_successor(current_state, applicable_ops);
		else if (params.type == Parameters::MDA)
			op = mda_successor(current_state, applicable_ops, params);
		else if (params.type == Parameters::MHA)
			op = mha_successor(current_state, applicable_ops, params);

		if (op == 0) {
			info->value = MRW::A_LOT;
			info->length_offset = length - i;
			if (params.type == Parameters::MDA)
				update_mda_action_values();
			return;
		}

		assert(op != 0);
		State succ_state = State(current_state, *op);
		info->path.push_back(op);
		if (check_goal(&succ_state)) {
			info->goal_visited = true;
			info->value = 0;
			info->length_offset = length - i;
			return;
		}
		current_state = succ_state;
	}

	if (heuristic != NULL)
    heuristic->set_recompute_heuristic();
	heuristic->evaluate(current_state);
	if (heuristic->is_dead_end()) {
		info->value = MRW::A_LOT;
		if (params.type == Parameters::MDA)
			update_mda_action_values();
		return;
	}

	int h = heuristic->get_heuristic();
	if (h == 0 && check_goal(&current_state)) {
		info->value = 0;
		info->goal_visited = true;
		return;
	}
	info->value = h;
	if (params.type == Parameters::MHA) {
		vector<const Operator *> helpful_actions;
		heuristic->get_preferred_operators(helpful_actions);
		update_mha_action_values(helpful_actions);
	}
	return;
}

const Operator* Walker::pure_successor(State & s, vector<const Operator *>& applicable_ops) {
	if (applicable_ops.size() == 0)
		return 0;
	int index = random() % applicable_ops.size();
	const Operator* op = applicable_ops[index];
  /*
  if(!op->is_applicable(s)){
    cout << "[MRW] " << op->get_name() << " is not applicable" << endl;
    for( int i = 1; i < applicable_ops.size(); i++){
      op = applicable_ops[(index+i)%applicable_ops.size()];
      if(op->is_applicable(s)){
        break;
      }
    }
  }*/
  //assert(op->is_applicable(s)); //Add by Lu
	return op;
}

const Operator* Walker::mha_successor(State & s, vector<const Operator *>& applicable_ops,
		Parameters& param) {
	if (applicable_ops.size() == 0)
		return 0;
	vector<double> probs;
	probs.resize(applicable_ops.size(), 0);
	double sum = 0;
	vector<int> unused_actions;
	int non_zero_q = 0;
	for (int i = 0; i < probs.size(); ++i) {
		int index = applicable_ops[i]->get_op_index();
		assert(index >= 0 && index < g_operators.size());

		if (num[index] == 0)
			unused_actions.push_back(i);
		if (Q[index] != 0) {
			sum += gibbs_func(Q[index], param.temperture);
			probs[i] = sum;
			non_zero_q++;
		}
	}
	if (unused_actions.size() == applicable_ops.size() || non_zero_q == 0) {
		int index = random() % applicable_ops.size();
		int op_index = applicable_ops[index]->get_op_index();
		num[op_index]++;
		return applicable_ops[index];
	}
	double coin = double(random()) / RAND_MAX;
	int final_index = -1;
	for (int i = 0; i < probs.size(); ++i) {
		if (coin < (probs[i] / sum)) {
			final_index = i;
			break;
		}
	}
	assert(final_index >= 0 && final_index < applicable_ops.size());
	int op_index = applicable_ops[final_index]->get_op_index();
	assert(Q[op_index] > 0);
	assert(op_index >= 0 && op_index < g_operators.size());
	num[op_index]++;
	const Operator* op = applicable_ops[final_index];
  /*if(!op->is_applicable(s)){
    cout << "[MRW] " << op->get_name() << " is not applicable" << endl;
    for( int i = 1; i < applicable_ops.size(); i++){
      op = applicable_ops[(final_index+i)%applicable_ops.size()];
      if(op->is_applicable(s)){
        break;
      }
    }
  }*/
 // assert(op->is_applicable(s)); //Add by Lu
	return op;
}

const Operator* Walker::mda_successor(State & s, vector<const Operator *>& applicable_ops,
		Parameters& param) {
	if (applicable_ops.size() == 0)
		return 0;
	vector<double> probs;
	probs.resize(applicable_ops.size(), 0);
	double sum = 0;
	vector<int> unused_actions;
	for (int i = 0; i < probs.size(); ++i) {
		int index = applicable_ops[i]->get_op_index();
		assert(index >= 0 && index < g_operators.size());
		if (num[index] != 0) {
			sum += gibbs_func(Q[index] / double(num[index]), param.temperture);
			probs[i] = sum;
		} else {
			unused_actions.push_back(i);
		}
	}
	if (unused_actions.size() != 0) {
		int index = random() % unused_actions.size();
		int op_index = applicable_ops[unused_actions[index]]->get_op_index();
		num[op_index]++;
		return applicable_ops[unused_actions[index]];
	}
	double coin = double(random()) / RAND_MAX;
	int final_index = -1;
	for (int i = 0; i < probs.size(); ++i) {
		if (coin < (probs[i] / sum)) {
			final_index = i;
			break;
		}
	}
	assert(final_index >= 0 && final_index < applicable_ops.size());
	int op_index = applicable_ops[final_index]->get_op_index();
	assert(op_index >= 0 && op_index < g_operators.size());
	num[op_index]++;
	const Operator* op = applicable_ops[final_index];
  /*if(!op->is_applicable(s)){
    cout << "[MRW] " << op->get_name() << " is not applicable" << endl;
    for( int i = 1; i < applicable_ops.size(); i++){
      op = applicable_ops[(final_index+i)%applicable_ops.size()];
      if(op->is_applicable(s)){
        break;
      }
    }
  }*/
  //assert(op->is_applicable(s)); //Add by Lu
	return op;
}

void Walker::update_mda_action_values() {
	for (int i = 0; i < info->path.size(); ++i) {
		int op_index = info->path[i]->get_op_index();
		assert(op_index >= 0 && op_index < g_operators.size());
		Q[op_index]--;
	}
}

void Walker::update_mha_action_values(vector<const Operator *> &helpful_actions) {
	for (int i = 0; i < helpful_actions.size(); ++i) {
		int op_index = helpful_actions[i]->get_op_index();
		assert(op_index >= 0 && op_index < g_operators.size());
		Q[op_index] += 1;
	}
}
double Walker::gibbs_func(double avg, float temperture) {
	return exp(avg / temperture);
}

