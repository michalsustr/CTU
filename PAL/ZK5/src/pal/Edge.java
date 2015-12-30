/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

/**
 *
 * @author michal
 */
class Edge implements Comparable {

    public int from, to, cost;

    public Edge() {
        // Default constructor for TreeSet creation
    }

    public Edge(int f, int t, int c) {
        // Inner class constructor
        from = f;
        to = t;
        cost = c;
    }

    @Override
    public int compareTo(Object o) {
        Edge other = (Edge) o;
        int cost1 = cost;
        int cost2 = other.cost;
        if (cost1 < cost2) {
            return (-1);
        } else if (cost1 == cost2) {
            return (0);
        } else {
            return (1);
        }
    }

    @Override
    public String toString() {
        return "Edge{" + "from=" + from + ", to=" + to + ", cost=" + cost + '}';
    }
}
