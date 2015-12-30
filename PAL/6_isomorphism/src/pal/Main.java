/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author michal
 */
public class Main {

    private static int N;
    private static int[] degrees;
    private static GraphGenerator gg;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        readInput();
        gg = new GraphGenerator();
        gg.generateGraphs();
        System.out.println("Result:");
        System.out.println(gg.getNonIsoGraphCount());
    }

    private static void readInput() throws IOException {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));

        N = sc.nextInt();
        degrees = new int[N];

        for (int i = 0; i < N; i++) {
            degrees[i] = sc.nextInt();
        }
    }

    private static class GraphGenerator {

        Graph g;
        GraphIsocheck graphsIsocheck;

        public GraphGenerator() {
            g = new Graph();
            graphsIsocheck = new GraphIsocheck();
        }

        public void generateGraphs() {
            iterate(0);
        }

        public int getNonIsoGraphCount() {
            return graphsIsocheck.count();
        }

        private void checkGraph() {
            graphsIsocheck.check(g);
        }

        private void iterate(int n) {
            iterate(n, n + 1);
        }

        private void iterate(int n, int offset) {
            if (n == N) {
                checkGraph();
            } else {
                if (g.nodes[n].remaining == 0) {
                    iterate(n + 1);
                } else {
                    for (int i = offset; i < N; i++) {
                        if (canConnect(n, i)) {
                            connect(n, i);
                            iterate(n, i + 1);
                            disconnect(n, i);
                        }
                    }
                }
            }
        }

        private boolean canConnect(int n, int i) {
            return g.nodes[n].remaining > 0 && g.nodes[i].remaining > 0;
        }

        private void connect(int n, int i) {
            g.nodes[n].connect(i);
            g.nodes[i].connect(n);
        }

        private void disconnect(int n, int i) {
            g.nodes[n].disconnect(i);
            g.nodes[i].disconnect(n);
        }
    }

    private static class Node {

        public int[] connections;
        public int degree, remaining;

        public Node(int degree) {
            this.connections = new int[N];
            for (int i = 0; i < N; i++) {
                connections[i] = 0;
            }
            this.degree = degree;
            this.remaining = degree;
        }

        public void connect(int n) {
            connections[n]++;
            remaining--;
        }

        public void disconnect(int n) {
            connections[n]--;
            remaining++;
        }

        public boolean hasConnectionTo(int n) {
            return connections[n] > 0;
        }
    }

    private static class GraphIsocheck {

        ArrayList<Graph> graphs;

        private GraphIsocheck() {
            this.graphs = new ArrayList<Graph>();
        }

        private void check(Graph g1) {
            boolean isIsomorphic = false;
            for (Graph g2 : graphs) {
                if (isomorphic(g1, g2)) {
                    isIsomorphic = true;
                    break;
                }
            }

            if (!isIsomorphic) {
                graphs.add(g1);
            }
        }

        private int count() {
            return graphs.size();
        }
        Graph g1, g2;
        ArrayList<Partition> pList1, pList2;
        int[] G1NodesToPartition;
        int[] isoMap;

        private boolean isomorphic(Graph g1, Graph g2) {
            pList1 = new ArrayList<Partition>();
            pList2 = new ArrayList<Partition>();
            getGraphPartitions(g1, pList1);
            getGraphPartitions(g2, pList2);

            if (pList1.size() != pList2.size()) {
                return false;
            }

            for (int i = 0; i < pList1.size(); i++) {
                Partition p1 = pList1.get(i);
                Partition p2 = pList2.get(i);

                if (p1.hasSameSignature(p2)) {
                    System.out.println("ano");
                    return false;
                }
            }

            fillG1NodesToPartition();

            isoMap = new int[N];
            for (int i = 0; i < N; i++) {
                isoMap[i] = 0;
            }

            return collectIsomorphisms(0);
        }

        private boolean collectIsomorphisms(int pos) {
            if (pos == N) {
                return true;
            }
            //const Graph::Node &node = g1.nodes.at(pos);
            int g1pIndex = G1NodesToPartition[pos];
            Partition p2 = pList2.get(g1pIndex);

            // pro tento uzel (pos) z g1 bereme uzly z odpovídající partition z g2
            for (int i = 0; i < p2.nodes.size(); i++) {
                int g2Index = p2.nodes.get(i);
                boolean ok = true;
                // budeme zkoušet, zda tento uzel z g2 může odpovídat uzlu z g1
                for (int j = 0; j < pos; ++j) {
                    int k = isoMap[j];
                    Node n1 = g1.nodes[j];
                    Node n2 = g2.nodes[k];

                    boolean g1_edge = n1.hasConnectionTo(pos);
                    boolean g2_edge = n2.hasConnectionTo(g2Index);

                    if (g1_edge != g2_edge) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    // možná, že odpovídá
                    isoMap[pos] = g2Index;
                    if (collectIsomorphisms(pos + 1)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void getGraphPartitions(Graph g, ArrayList<Partition> partitions) {
            // key:   vector of degrees of neighbours (eg signature)
            // value: indices of the nodes that share this property
            HashMap<ArrayList<Integer>, ArrayList<Integer>> partitionMap =
                    new HashMap<ArrayList<Integer>, ArrayList<Integer>>();

            for (int i = 0; i < g.nodes.length; ++i) {
                Node current = g.nodes[i];
                ArrayList<Integer> signature = new ArrayList<Integer>(N - 1);
                for (int j = 0; j < N - 1; j++) {
                    signature.add(0);
                }
                for (int j = 0; j < current.connections.length; ++j) {
                    if (current.connections[j] == 0) {
                        continue;
                    }
                    Node neighbour = g.nodes[j];
                    //std::cerr << "      incrementing signature degree " << connected_degree << " (from node " << connected_node_index << ") of node " << ni << std::endl;
                    signature.set(neighbour.degree, signature.get(neighbour.degree) + 1);
                }
                //std::cerr << "      node " << ni << " signature: " << to_str(signature) << std::endl;
                ArrayList<Integer> signatureNodes;
                if (!partitionMap.containsKey(signature)) {
                    signatureNodes = new ArrayList<>();
                } else {
                    signatureNodes = partitionMap.get(signature);
                }
                signatureNodes.add(i);
                partitionMap.put(signature, signatureNodes);
            }

            for (ArrayList<Integer> signature : partitionMap.keySet()) {
                partitions.add(new Partition(signature, partitionMap.get(signature)));
            }
        }

        void fillG1NodesToPartition() {
            G1NodesToPartition = new int[N];

            for (int i = 0; i < pList1.size(); i++) {
                Partition p = pList1.get(i);
                int cnt = p.nodes.size();
                for (int j = 0; j < cnt; j++) {
                    G1NodesToPartition[p.nodes.get(j)] = i; // fill the index of partition to the nodes
                }
            }

            System.out.println("fill:");
            for(int as : G1NodesToPartition) {
                System.out.print(" "+as);
            }
            System.out.println("---");

        }
    ;

    }

    private static class Graph {

        public Node nodes[];

        public Graph() {
            nodes = new Node[N];
            for (int i = 0; i < N; i++) {
                nodes[i] = new Node(degrees[i]);
            }
        }

        private void print() {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.print("" + nodes[i].connections[j]);
                }
                System.out.println("");
            }
            System.out.println("-----");
        }
    }

    private static class Partition {

        ArrayList<Integer> signature;
        ArrayList<Integer> nodes;

        public Partition(ArrayList<Integer> signature, ArrayList<Integer> nodes) {
            this.signature = signature;
            this.nodes = nodes;
        }

        private boolean hasSameSignature(Partition p2) {
            if (signature.size() != p2.signature.size()) {
                return false;
            }

            for (int i = 0; i < signature.size(); i++) {
                if (signature.get(i) != p2.signature.get(i)) {
                    return false;
                }
            }
            return true;
        }
    }
}
