/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author michal
 */
public class Node {
    private TreeSet<Edge> edges;
    private int index;

    public Node(int index) {
        edges = new TreeSet<Edge>();
        this.index = index;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public TreeSet<Edge> getEdges() {
        return edges;
    }
}
