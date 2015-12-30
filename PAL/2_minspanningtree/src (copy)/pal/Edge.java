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

    public boolean equals(Object obj) {
        // Used for comparisions during add/remove operations
        Edge e = (Edge) obj;
        return (cost == e.cost &&
                ((from == e.from && to == e.to) || (from == e.to && to ==e.from)));
    }

    @Override
    public String toString() {
        return "Edge{" + "from=" + from + ", to=" + to + ", cost=" + cost + '}';
    }

    @Override
    public int compareTo(Object o) {
        Edge other = (Edge) o;
        int cost1 = cost;
        int cost2 = other.cost;
        int from1 = from;
        int from2 = other.from;
        int to1 = to;
        int to2 = other.to;

        if (cost1 < cost2) {
            return (-1);
        } else if (cost1 == cost2 && from1 == from2 && to1 == to2) {
            return (0);
        } else if (cost1 == cost2) {
            return (-1);
        } else if (cost1 > cost2) {
            return (1);
        } else {
            return (0);
        }
    }

    public Edge getReverseEdge() {
        return new Edge(to, from, cost);
    }
}
