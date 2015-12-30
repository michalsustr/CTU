/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 *
 * @author michal
 */
public class Main {

    static TreeSet<Edge> edges;
    static HashSet<Integer> connectedCmps[];
    static ArrayList<Integer> A;
    static ArrayList<Node> nodes;
    static int K;
    private static int thisCombinationCost;
    private static int lowestCost;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        loadInput();

        // Generate sets of B
        // prepare indices ...
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < K-1; i++) {
            indices.add(i);
        }
        // this is a bit of hack, the last element is set to the value of the before last
        // so that getNextCombination gives us the right combination
        indices.add(indices.size() - 1);
        lowestCost = Integer.MAX_VALUE;
        while (true) {
            indices = getNextCombination(indices, A.size());
            if (indices == null) {
                break;
            }

            int cost = getHedgehogSpanningTreeCost(indices);
//            System.out.println("Cost "+cost);
            if (cost == -1) { // combination is not feasible
                continue;
            }
            if (lowestCost > cost) {
                lowestCost = cost;
            }
        }

        if (lowestCost == Integer.MAX_VALUE) {
            System.out.println("-1");
        } else {
            System.out.println(lowestCost);
        }
    }

    private static void loadInput() {
        // load input
        Scanner sc = new Scanner(System.in);

        int numNodes = sc.nextInt();
        edges = new TreeSet<Edge>();
        nodes = new ArrayList<Node>(numNodes);
        connectedCmps = new HashSet[numNodes];
        for (int i = 0; i < numNodes; i++) {
            Node node = new Node(i);
            for (int j = 0; j < numNodes; j++) {
                int cost = sc.nextInt();
                if (j > i && cost != 0) {
                    Edge edge = new Edge(i, j, cost);
                    edges.add(edge);
                    node.addEdge(edge);
                }
                if (j < i && cost != 0) {
                    node.addEdge(new Edge(i, j, cost));
                }
            }
            nodes.add(node);
        }


        int numSubgraph = sc.nextInt();
        A = new ArrayList<Integer>(numSubgraph);
        for (int i = 0; i < numSubgraph; i++) {
            A.add(sc.nextInt() - 1);
        }

        K = sc.nextInt();
    }

    private static List<Integer> getNextCombination(List<Integer> indices, int Asize) {
        if (indices.get(indices.size() - 1) + 1 < Asize) {
            indices.set(indices.size() - 1, indices.get(indices.size() - 1) + 1);
            return indices;
        }
        if (indices.size() == 1) {
            return null;
        }
        List<Integer> newIndices = getNextCombination(indices.subList(0, indices.size() - 1), Asize - 1);
        if (newIndices == null) {
            return null;
        }
        if (newIndices.get(newIndices.size() - 1) == Asize - 1) {
            return null;
        }
        newIndices.add(newIndices.get(newIndices.size() - 1) + 1);
        return newIndices;
    }

    private static HashSet<Edge> spanningTree;

    private static int getHedgehogSpanningTreeCost(List<Integer> indices) {
        // fix the nodes with given indices
        HashSet<Integer> B = new HashSet<Integer>();
        spanningTree = new HashSet<Edge>();
        thisCombinationCost = 0;
        for (int i = 0; i < nodes.size(); i++) {
            connectedCmps[i] = new HashSet(nodes.size());
            connectedCmps[i].add(i);
        }
        for (int i = 0; i < indices.size(); i++) {
            B.add(A.get(indices.get(i)));
        }
        for(int i = 0;  i < indices.size(); i++) {
            Edge edge = getCheapestEdgeNotBetweenB(nodes.get(A.get(indices.get(i))), B);
            if(edge == null) {
                return -1;
            }
            if(!addEdge(edge)) {
                return -1;
            }

            connectedCmps[edge.from].add(edge.to);
            connectedCmps[edge.to].add(edge.from);
        }
        //System.out.println("B RST"+rst);

        for(Edge edge : edges) {
            if(spanningTree.contains(edge)) {
                continue;
            }
            // nodes are not in different sets, therefore this edge would cause a cycle
            if (connectedCmps[edge.from] == connectedCmps[edge.to]) {
                continue;
            }
            if(B.contains(edge.from) || B.contains(edge.to)) {
                continue;
            }

            // merge connected components together and update all relevant nodes
            HashSet<Integer> from = connectedCmps[edge.from], to = connectedCmps[edge.to];
            for (Integer node : from) {
                to.add(node);
            }
            for (Integer node : to) {
                connectedCmps[node] = to;
            }
            if(!addEdge(edge)) {
                return -1;
            }
            if(to.size() == nodes.size()) {
                break;
            }
        }
        if(connectedCmps[0].size() != nodes.size()) {
            return -1;
        }
        return thisCombinationCost;
    }
    private static Edge getCheapestEdgeNotBetweenB(Node node, HashSet<Integer> B) {
        for(Edge edge : node.getEdges()) {
            if (!B.contains(edge.from) || !B.contains(edge.to)) {
                return edge;
            }
        }
        return null;
    }

    private static boolean addEdge(Edge edge) {
        if(thisCombinationCost+edge.cost > lowestCost) {
            return false;
        }
        spanningTree.add(edge);
        thisCombinationCost += edge.cost;
        return true;
    }
}
