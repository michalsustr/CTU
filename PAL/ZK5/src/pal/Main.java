/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;
/**
 *
 * @author michal
 */
public class Main {
    private static int N,M,D;


    static HashSet<Integer> connectedCmps[];
    static TreeSet<Edge> edges;
    static ArrayList<Edge> T;
    static ArrayList<Edge> nonT;

    static int maxDif = 0;
    private static int maxPrice;
    private static int Tprice;


    static Node[] nodes;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        loadInput();

        nodes = new Node[N];
        for (int i = 0; i < N; i++) {
            nodes[i] = new Node();
            nodes[i].id = i;
        }

        T    = new ArrayList<Edge>();
        nonT = new ArrayList<Edge>();
        primAlg();

        int maxPrice = 0;
        Edge maxE2 = null, maxE1 = null;

        for (Edge e1 : T) {
            for(Edge e2 : nonT) {
                if(e2.cost - e1.cost < 0) continue;
                int price = Tprice+e2.cost-e1.cost;
                if(price > maxPrice) {
                    System.out.println("possibly "+price);
                    if(canBreak(e1,e2)) {
                        maxPrice = price;
                        maxE2 = e2;
                        maxE1 = e1;
                        System.out.println("newmax");
                    }
                    System.out.println("---");
                }
            }
        }
        T.add(maxE2);
        T.remove(maxE1);
        nonT.add(maxE1);
        nonT.remove(maxE2);

        for (Edge e:T) {
            System.out.println(e);
        }

        System.out.println(maxPrice);
    }


     private static void loadInput() throws IOException {
        // load input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int node1, node2, w, k;
        String line;

         line = br.readLine() + " ";      // add sentinel
        k = 0;
        node1 = 0;
        node2 = 0;
        w = 0;

        while (line.charAt(k) != ' ') { // get first node
            node1 = node1 * 10 + line.charAt(k++) - 48;
        }

        while (line.charAt(++k) != ' ') {// get second node
            node2 = node2 * 10 + line.charAt(k) - 48;
        }

        while (line.charAt(++k) != ' ') {// get weight
            w = w * 10 + line.charAt(k) - 48;
        }

        N = node1;
        M = node2;
        D = w;

        edges = new TreeSet<Edge>();

         for (int i = 0; i < M; i++) {
            line = br.readLine() + " ";      // add sentinel
            k = 0;
            node1 = 0;
            node2 = 0;
            w = 0;

            while (line.charAt(k) != ' ') { // get first node
                node1 = node1 * 10 + line.charAt(k++) - 48;
            }

            while (line.charAt(++k) != ' ') {// get second node
                node2 = node2 * 10 + line.charAt(k) - 48;
            }

            while (line.charAt(++k) != ' ') {// get weight
                w = w * 10 + line.charAt(k) - 48;
            }

            edges.add(new Edge(node1, node2, w));
        }
    }


    static boolean canBreak(Edge e1, Edge e2) {
        // try remove e1 and add e2
        nodes[e1.from].neighbours.remove(nodes[e1.to]);
        nodes[e1.to].neighbours.remove(nodes[e1.from]);
        nodes[e2.from].neighbours.add(nodes[e2.to]);
        nodes[e2.to].neighbours.add(nodes[e2.from]);

        for (int i = 0; i < N; i++) {
            nodes[i].visited = false;
        }

        loops = 0;
        System.out.println("Edges "+e1+" "+e2);
        boolean detected = detectCycleDFS(0, 0);
        if(detected) {
            // put back to original state
            nodes[e1.from].neighbours.add(nodes[e1.to]);
            nodes[e1.to].neighbours.add(nodes[e1.from]);
            nodes[e2.from].neighbours.remove(nodes[e2.to]);
            nodes[e2.to].neighbours.remove(nodes[e2.from]);
            System.out.println("cannot");
            return false;
        }

        System.out.println("can");

        return true;
    }

    private static int loops;
    private static boolean detectCycleDFS(int i, int parent) {
        loops++;
        if(nodes[i].visited) return true;
        nodes[i].visited = true;
        System.out.println("from "+i);
        for(Node n : nodes[i].neighbours) {
            System.out.println("    to "+n.id);
            if(n.id == parent) continue;
            if(detectCycleDFS(n.id, i)) {
                return true;
            }
        }
        return false;
    }

    static void primAlg() {

        DisjointSet ds = new DisjointSet();
        int i = 0;
        Tprice = 0;
        for(Edge e: edges) {
            if(ds.find(e.from) != ds.find(e.to)) {
                T.add(e);
                nodes[e.from].neighbours.add(nodes[e.to]);
                nodes[e.to].neighbours.add(nodes[e.from]);
                Tprice += e.cost;
                ds.union(e.from, e.to);
            } else {
                nonT.add(e);
            }
        }
    }



    /**
     *
     * @author michal
     */
    private static class DisjointSet {

        /**
         * Provede sjednoceni komponent, ve kterych jsou uzly "a" a "b"
         * Union provadi vzdy do A
         * @param a cislo uzlu a
         * @param b cislo uzlu b
         * @return cislo reprezentanta sjednocene komponenty
         */
        public int union(int a, int b) {
            Node repA = nodes[find(a)];
            Node repB = nodes[find(b)];

            repB.parent = repA;
            return repA.id;
        }

        /**
         * Vrati reprezentanta zadaneho uzlu
         * @param a cislo uzlu, jehoz reprezentanta hledame
         * @return cislo reprezentanta uzlu
         */
        public int find(int a) {
            Node n = nodes[a];
            int jumps = 0;
            while (n.parent != null) {
                n = n.parent;
                jumps++;
            }
            if (jumps > 1) repair(a, n.id);
            return n.id;
        }

        /**
         * Provede opravu (compression) cesty
         * @param a
         * @param rootId
         */
        private void repair(int a, int rootId) {
            Node curr = nodes[a];
            while (curr.id != rootId) {
                Node tmp = curr.parent;
                curr.parent = nodes[rootId];
                curr = tmp;
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < nodes.length; i++) {
                builder.append(find(i) + " ");
            }
            return builder.toString();
        }
    }
}
