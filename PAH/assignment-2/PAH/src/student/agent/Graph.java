/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.agent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.vecmath.Point2d;

/**
 *
 * @author michal
 */
public class Graph {

    int N;
    ArrayList<Node> nodes;
    double[][] d; // distances matrix for dijkstra input
    public int[] predecessors;
    public double[] distances;

    public Graph() {
        nodes = new ArrayList<Node>();
    }



    void add(Node node) {
        nodes.add(node.id, node);
    }

    void initDistances() {
        N = nodes.size();
        d = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                d[i][j] = Double.MAX_VALUE;
            }
        }
    }

    void addEdge(Node n1, Node n2) {
        double dist = new Point2d(n1.p.x, n1.p.y).distance(new Point2d(n2.p.x, n2.p.y));
        d[n1.id][n2.id] = dist;
        d[n2.id][n1.id] = dist;
        n1.addEdge(n2);
        n2.addEdge(n1);
    }

    /**
     * Dijkstruv algoritmus
     *
     * @param d matice delek (Integer.MAX_VALUE pokud hrana mezi uzly
     * neexistuje)
     * @param from uzel ze ktereho se hledaji nejkratsi cesty
     * @return strom predchudcu (z ciloveho uzlu znaci cestu do uzlu from)
     */
    public void dijkstra(int from) {
        Set<Integer> set = new HashSet<Integer>();
        set.add(from);

        boolean[] closed = new boolean[d.length];
        distances = new double[d.length];
        for (int i = 0; i < d.length; i++) {
            if (i != from) {
                distances[i] = Integer.MAX_VALUE;
            } else {
                distances[i] = 0;
            }
        }


        predecessors = new int[d.length];
        predecessors[from] = -1;

        while (!set.isEmpty()) {
            //najdi nejblizsi dosazitelny uzel
            double minDistance = Integer.MAX_VALUE;
            int node = -1;
            for (Integer i : set) {
                if (distances[i] < minDistance) {
                    minDistance = distances[i];
                    node = i;
                }
            }

            set.remove(node);
            closed[node] = true;

            //zkrat vzdalenosti
            for (int i = 0; i < d.length; i++) {
                //existuje tam hrana
                if (d[node][i] != Double.MAX_VALUE) {
                    if (!closed[i]) {
                        //cesta se zkrati
                        if (distances[node] + d[node][i] < distances[i]) {
                            distances[i] = distances[node] + d[node][i];
                            predecessors[i] = node;
                            set.add(i); // prida uzel mezi kandidaty, pokud je jiz obsazen, nic se nestane
                        }
                    }
                }
            }
        }
    }
}
