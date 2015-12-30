package kui;

/**
 * Implementation of Greedy algorithm, based on A* algorithm
 * setting the g (cost) function as zero
 * 
 * @author Michal Sustr
 */
public class Greedy extends AStar {
	protected double gValue(Node node) {
		return 0;
	}
	
	/**
	 * gValue between two neighbouring nodes
	 */
	protected double gValue(Node current, Node next) {
		return 0;
	}
}
