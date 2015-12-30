package kui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of A* algorithm
 * 
 * @author Michal Sustr
 * @link http://en.wikipedia.org/wiki/A*_search_algorithm
 * @link https://cw.felk.cvut.cz/lib/exe/fetch.php/courses/a3b33kui/public_files/prednasky/prednaska_06.pdf
 */
public class AStar extends InformedSearchingAlgorithm {

	/* List of nodes that have to be investigated
	 *
	 * The Double value determines the cost that is needed
	 * to get to the specified Node from the starting one
	 */
	HashMap<Node, Double> open;

	/* List of closed nodes (nodes that have been already expanded)
	 *
	 * This list serves to prevent loops
	 */
	LinkedList<Node> closed;

	/* Store hierarchy of nodes to backtrack the route
	 * from endNode to startNode
	 *
	 * Map is in form <child, parent>
	 */
	HashMap<Node, Node> hierarchy;

	/* The way to return
	 * 
	 * A list of nodes from startNode to endNode containing the required path
	 */
	LinkedList<Node> way;
	
	public List<Node> findWay(Node startNode, Node endNode) {
		open = new HashMap<Node, Double>();
		closed = new LinkedList<Node>();
		hierarchy = new HashMap<Node, Node>();
		way = new LinkedList<Node>();

				/* Other neccessary vars */
		int cycle = 0; // prevent recursion
		double minValue; Node minNode = null;
		double cost, currentCost;
		Node current, next, track = null;
		Iterator<Node> it = null;
		List<Node> expand = null;
		boolean addToHierarchy;

		// start node = end node
		if(startNode.isTarget()) {
			return null;
		}

		// start open list with the startNode
		open.put(startNode, 0.);

		while(!open.isEmpty()) {
			// take the node with the least value of the f-function
			it = open.keySet().iterator();
			minValue = Double.MAX_VALUE;
			while(it.hasNext()) {
				next = it.next();
				// f = g + h
				cost = gValue(next) + hValue(next, endNode);
				if(cost < minValue) {
					minValue = cost;
					minNode = next;
				}
			}
			current = minNode;
			currentCost = gValue(current); // cost g
						
						// add to closed list and remove from open
			closed.add(current);
			open.remove(current);

			// target has been found
			if(current.isTarget()) {
				// backtrack the tree
				track = current;
				way.add(track);

				// climb up the hierarchy
				while(!track.equals(startNode)) {
					track = hierarchy.get(track);
					way.push(track);
				}
				return way;
			}
						
						// current node is not target, expand neighbours
			expand = current.expand();
			it = expand.iterator();
			while(it.hasNext()) {
				next = it.next();
				if(closed.contains(next)) {
					continue;
				}

				// value of g function for neighbour of expanded node
				cost = currentCost + gValue(current, next);

				addToHierarchy = true;
				// open doesn't contain neighbour, simply add it to the list
				// and update hierarchy
				if(!open.containsKey(next)) {
					open.put(next, cost);
					
				// there is already this node in open, rewrite its cost
				// and update hierarchy
				} else if(open.get(next) > cost) {
					open.put(next, cost);
					
				// this node is already in open, but the cost of a new path
				// by which we got into this node is higher than cost of the previous path
				} else {
					addToHierarchy = false;
				}

				if(addToHierarchy) {
					hierarchy.put(next, current);
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
	
	
	protected double gValue(Node node) {
		return open.get(node);
	}
	
	/**
	 * gValue between two neighbouring nodes
	 */
	protected double gValue(Node current, Node next) {
		return Math.sqrt(Math.pow(next.getX()-current.getX(), 2)+ Math.pow(next.getY()-current.getY(), 2));
	}
	
	
	protected double hValue(Node next, Node endNode) {
		return Math.sqrt(Math.pow(next.getX()-endNode.getX(), 2) + Math.pow(next.getY()-endNode.getY(), 2));
	}

}
