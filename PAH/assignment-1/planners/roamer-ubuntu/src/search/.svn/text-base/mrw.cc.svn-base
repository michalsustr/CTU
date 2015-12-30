#include "mrw.h"
#include "globals.h"
#include "walker.h"
#include "successor_generator.h"
#include "heuristic.h"
#include <stdlib.h>
/*
#ifdef STOC
#include <pthread.h>
pthread_mutex_t mrw_mutex = PTHREAD_MUTEX_INITIALIZER;
#endif
*/
MRW::MRW(State const * sp) :	current_state(*sp) {
  initial_state = sp;
	info = new WalkInfo;
}

MRW::~MRW() {
}

void MRW::set_id(int i) {
	id = i;
}



int MRW::step() {
  num_step++;
	if (g_mrw_stop <= 0) {
#ifdef VERBOSE
		cout << "[MRW" << id << "] Respect solutions from other threads\n";
#endif
		return SOLVED;
	}
	current_min = A_LOT;

	int num_dead_ends = 0;
	int progress = 0;
	Path best_path;
	avg_branching = 0;
	failure_percentage = 0;

	//int last_effective_walk = 0;
	double length_walk = params.length_walk;
	double length_jump = params.length_jump;

	Walker walker = Walker(current_value);
	walker.set_info(info);
	walker.set_heuristic(heuristic);

	int i;
	for (i = 0; i < params.num_walk; ++i) {
		if (g_mrw_stop <= 0) {
			return SOLVED;
		} // skip some jump
		walker.random_walk(current_state, int(length_walk), params);
		assert(length_walk != 0 );

		// It happens that the length of walk is smaller
		// than what was expected (it might hit a dead-end)
		avg_branching += info->branching / (int(length_walk)
				+ info->length_offset);
		if (g_mrw_stop <= 0) {
			return SOLVED;
		}
		if (info->goal_visited) { // MRW found a plan
			//current_state = jump(current_state, info->path, int(length_jump));
			plan.insert(plan.end(), info->path.begin(), info->path.end());
			//current_state = jump(*initial_state, plan, plan.size());
			//set_plan(plan);
#ifdef VERBOSE
			cout << "[MRW" << id << "] Found solution\n";
#endif
      store_plan();
      g_mrw_stop = 0;
			return SOLVED;
		}

		if (info->value == A_LOT) {
			num_dead_ends++;
			continue;
		}

		//int pre_value = current_min;
		update_current_min(info->value, info->path, best_path);
		progress = total_min - info->value;
		if (progress > acceptable_progress && !(current_state	== (*initial_state)))
			break;

		// Increase the length of the walk if n last walks have not been effective.
		/*if (params.deepening) {
			int n = int(params.num_walk * params.extending_period);
			if (current_min < pre_value)
				last_effective_walk = i;
			else if (i - last_effective_walk > n) {
				length_walk = length_walk * params.extending_rate;
				length_jump = length_jump * params.extending_rate;
				last_effective_walk = i;
			}
		}*/
	}

	update_acceptable_progress();

	avg_branching = int(avg_branching / float(i + 1));
	failure_percentage = num_dead_ends / float(i + 1);
  check_fallback_strategies();

	if (best_path.size() == 0) {
		restart(DEADEND); // Deadend
		return IN_PROGRESS;
	}

	current_state = jump(current_state, best_path, int(length_jump));
	current_value = current_min;
	//cout << "[MRW" << id << "] h_min: " << current_min << "/heu bound: " << heu_bound << endl; // << " [ " << evaluated_states << " evaluated ]" << endl; 
	//cout << "[MRW" << id << "] current_min: " << current_min << endl; // << " [ " << evaluated_states << " evaluated ]" << endl; 
	plan.insert(plan.end(), best_path.begin(), best_path.end());

	update_total_min();
  //Add by Lu
  if(total_min < step_cost_bound && total_min != DEAD_END){
  //if(total_min < heu_bound){
    g_mrw_stop--;
    store_plan();
    return SOLVED;
  }
  /*
	if (num_jumps > params.max_steps) {
		//restart(TOO_DEEP);
		//Lu
    store_plan();
    return FAILED;
		//return IN_PROGRESS;
	}*/
  
  if (num_step * params.length_walk > params.steps_bound){
    store_plan();
    return FAILED;
  }
	return IN_PROGRESS;
}


void MRW::check_fallback_strategies() {
	if (params.type != Parameters::PURE)
		return;
  //Lu set parameters of num_walk and length_walk
  //Note that: do not change max_steps
	int n = params.num_walk;
	int l = params.length_walk;
	if (avg_branching > 1000) {
		params = Parameters(1.5, 10, n, l, Parameters::MHA);
		cout << "[MRW" << id << "] MHA is activated" << endl;
		return;
	}

	if (failure_percentage > 0.5) {
		params = Parameters(2, 0.5, n, 1, Parameters::MDA);
    //Lu MDA's length_walk = 1
    params.steps_bound = params.max_steps;
#ifdef VERBOSE
		cout << "[MRW" << id << "] MDA is activated" << endl;
#endif
		return;
	}
}

/* Jump to a new state also feed states
 * TODO: implement store+jump. need a closed-list.
 */
#ifdef STOC
State MRW::store_jump(State state, Path& path, int jump_length) {
	for (int i = 0; i < path.size() && (i < jump_length || goal_visited); ++i) {
		State succ_state = State(state, *(path[i]));
		state = succ_state;
	}
	return state;
}
#endif

State MRW::jump(State state, Path& path, int jump_length) {
  assert(path.size() <= jump_length);
	for (int i = 0; i < path.size() && (i < jump_length || goal_visited); ++i) {
		State succ_state = State(state, *(path[i]));
		state = succ_state;
	}
	return state;
}

void MRW::dump(int num_dead_ends, int period, int iteration) {
	cout << "period: " << period << endl;
	cout << "average improvement: " << acceptable_progress << endl;
	cout << "num of simulations: " << iteration << endl;
	cout << "num of deadends: " << num_dead_ends << endl;
	cout << "branching: " << avg_branching << endl;
	cout << "current jumps: " << num_jumps << endl;
}

void MRW::update_acceptable_progress() {
	int improvement = total_min - current_min;
	if (improvement < 0)
		improvement = 0;

	if (current_state == (*initial_state))
		acceptable_progress = improvement;
	else
		acceptable_progress = (1 - params.alpha) * acceptable_progress + params.alpha * improvement;

	if (acceptable_progress < 0.001)
		acceptable_progress = 0;
}

void MRW::add_heuristic(Heuristic * h, bool use_estimates,
		bool use_preferred_operators) {

	assert(use_estimates);
	// cout << use_estimates;

	if (use_preferred_operators) {
		cout << "Warning: preferred ops are not supported in mrw" << endl;
	}

	heuristic = h;
	// Walker::set_heuristic(heuristic);
	// cout << "[MRW" << id <<"] Finished setting heuristics\n";

}

void MRW::initialize() {
	num_jumps = 0;
  num_step = 0;
	goal_visited = false;
	plan.clear();
  assert(initial_state);
	current_state = *initial_state;
	if (heuristic != NULL)
    heuristic->set_recompute_heuristic();
	heuristic->evaluate(current_state);
  //cout << "[MRW] evaluate done" <<endl;
	if (heuristic->is_dead_end()) {
		assert(heuristic->dead_ends_are_reliable());
		cout << "[MRW] Initial state is a dead end." << endl;
    //Lu
    g_mrw_stop = 0;
    return;
	}
	total_min = heuristic->get_heuristic();
  if(total_min <= 0)
    g_mrw_stop = 0;

	initial_value = total_min;
	current_value = initial_value;
	//g_state_value[0] = total_min; // A hack for now
  int n, l;
  if(g_stoc_instances == 1){
    n = 1000;
    l = 5;
  }else{
    n = 200+(1000-200)*id/(g_stoc_instances-1);
    l = 1+(10-1)*id/(g_stoc_instances-1);
  }
	params = Parameters(1.5, 0, n, l, Parameters::PURE);

#ifdef VERBOSE
	cout << "[MRW] initial heuristic value: " << initial_value << "/" << step_cost_bound << endl;
#endif
}

void MRW::update_current_min(int h, Path& temp_path, Path& min_path) {
	if (h == A_LOT)
		return;
	if (h < current_min) {
    /*
    if (current_min <= total_min){
			cout << "[MRW" << id << "] h_min: " << current_min << "/heu bound: " << heu_bound << endl; // << " [ " << evaluated_states << " evaluated ]" << endl; 
    }*/
		current_min = h;
		min_path = temp_path;
	}
}

void MRW::restart(int indicate) {
#ifdef VERBOSE
  cout << "[MRW" << id << "] Restart " ;
  if (indicate == DEADEND) { 
    cout << "from a deadend. " << endl;
  } else { 
    cout << "from a deep local minimal." << endl;
  }
#endif
	plan.clear();
	num_jumps = 0;
  
  //Add by Lu
	total_min = initial_value;
	current_value = initial_value;
  assert(initial_state);
	current_state = *initial_state; 
}

void MRW::update_total_min() {
	if (current_min < total_min) {
		total_min = current_min;
		//cout << "[MRW" << id << "] total_min: " << total_min << endl; // << " [ " << evaluated_states << " evaluated ]" << endl; 
		num_jumps = 0;
	} else
		num_jumps++;
}

/* The following two functions are added for changing the value of parameters
 * in the runtime after MRW is constructed. 
 * The default initializer takes no parameters
 */

void MRW::set_numwalk(int nw) {
	params.num_walk = nw;
}

void MRW::set_maxsteps(int ms) {
	params.max_steps = ms;
}

//Add by Lu
void MRW::set_heu_bound(int heu){
  heu_bound = heu;
  //cout <<"[MRW" << id << "] set the plateau heuristic value: " << heu_bound << "\n";
}

void MRW::set_step_cost_bound(int cost){
  step_cost_bound = cost;
  //cout <<"[MRW" << id << "] set the plateau heuristic value: " << heu_bound << "\n";
}


void MRW::set_init_state(const State * sp){
  initial_state = sp;
}

#ifdef STOC
void MRW::store_plan() {
  if(plan.size() <=0)
    return;

  MRWPlan *p = new MRWPlan;
  p->init_state = initial_state;
  p->plan.insert(p->plan.end(), plan.begin(), plan.end());
  
  //pthread_mutex_lock(&mrw_mutex);
  g_MRWPlan.push_back(p);
  //pthread_mutex_unlock(&mrw_mutex);

  if( total_min != DEAD_END && total_min < step_cost_bound )
    cout << "[MRW" << id << "] find a better state " << total_min << "/" << step_cost_bound << " (" << p->plan.size()<< " steps) (params: " << params.num_walk << " " << params.length_walk << ")" << endl;
  else
    cout << "[MRW" << id << "] expand a path (" << p->plan.size() << " steps) (params: " << params.num_walk << " " << params.length_walk << ")" << endl;
}
#endif
