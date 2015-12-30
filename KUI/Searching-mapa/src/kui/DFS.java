package kui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Implementation of Depth-First-Search
 * 
 * The only difference from BFS is the order in which we add new expanded nodes
 * into the open list - BFS adds expanded nodes at the end of open, DFS at the beginning
 * @author Michal Sustr
 * @link https://cw.felk.cvut.cz/lib/exe/fetch.php/courses/a3b33kui/public_files/prednasky/prednaska_05.pdf
 */
public class DFS extends BFS {
	public List<Node> findWay(Node startNode) {
		open = new LinkedList<Node>();
		closed = new LinkedList<Node>();
		hierarchy = new HashMap<Node, Node>();
		way = new LinkedList<Node>();
	    
		int cycle = 0; // prevent recursion
		Node current, next, track = null;
		List<Node> expand = null;
		

		// start node = end node
		if(startNode.isTarget()) {
			return null;
		}

		// start open list with the startNode
		open.add(startNode);

		while(!open.isEmpty()) {
			// take the first node from open
			current = open.poll();

			// this node has not been investigated yet (prevents recursion)
			if(!closed.contains(current)) {
				expand = current.expand();

				closed.add(current);
				Iterator<Node> it = expand.iterator();
				while(it.hasNext()) {
					next = it.next();
					if(!hierarchy.containsKey(next)) {
						hierarchy.put(next, current);
					}
					// target has been found
					if(next.isTarget()) {
						// backtrack the tree
						track = next;
						way.add(track);
						
						while(!track.equals(startNode)) {
							track = hierarchy.get(track);
							way.push(track);
						}
						return way;
					} else {
						open.push(next);
					}
				}
			}

			cycle++;
			if(cycle > 1E8) {
				System.err.println("too many cycles, ending");
				break;
			}
		}

		// could not find the way
		return null;
	}
}
