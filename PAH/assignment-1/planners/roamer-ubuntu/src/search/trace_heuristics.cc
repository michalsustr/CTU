// typedef std::vector<const Operator *> Plan;
// closed_list.cc is too generic to put this in

#include "globals.h"
#include "switches.h"
#include "state.h"

using namespace std;

void trace_states(State& current_state,  vector<int>* s) {
	State current_entry = entry;
	for(;;) {
		const PredecessorInfo &info = closed.find(current_entry)->second;
		if(info.predecessor == 0)
		break;
		path.pushback(current_entry->min_value);
		// path.pushback(current_entry_ current_vale); // TODO the current value
		current_entry = *info.predecessor;
	}
	reverse(path.begin(), path.end());
}
