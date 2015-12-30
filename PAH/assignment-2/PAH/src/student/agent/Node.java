/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.agent;

import cz.agents.alite.tactical.util.Point;
import java.util.ArrayList;
import javax.vecmath.Point2d;

/**
 *
 * @author michal
 */
public class Node {
   int id;
   Point p;
   ArrayList<Node> neighbours;
   ArrayList<Double> distances;

    Node(Point p, int id) {
        this.neighbours = new ArrayList<Node>();
        this.distances = new ArrayList<Double>();
        this.p = p;
        this.id = id;
    }

    void addEdge(Node node) {
        neighbours.add(node);
    }
}