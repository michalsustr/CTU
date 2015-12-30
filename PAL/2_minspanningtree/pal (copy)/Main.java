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

    static TreeSet<Edge> allEdges;
    static HashSet<Integer> connectedCmps[];
    static ArrayList<Integer> A;
    static ArrayList<Edge> spanningTree;
    static ArrayList<Edge> reducedSpanningTree;
    static TreeSet<Edge> reducedEdges;
    static ArrayList<Node> nodes;
    static int K;
    private static int thisCombinationCost;
    private static int lowestCost;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        loadInput();

        // Find minimum spanning tree for the whole graph using Kruskal algorithm
        minimumSpanningTree();

        // Throw out all nodes and corresponding edges from set A
        reduceSpanningTree();

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
        allEdges = new TreeSet<Edge>();
        // index is the number of node and hashset is the set of nodes
        // that are connected
        connectedCmps = new HashSet[numNodes];
        nodes = new ArrayList<Node>(numNodes);
        for (int i = 0; i < numNodes; i++) {
            Node node = new Node(i);
            for (int j = 0; j < numNodes; j++) {
                int cost = sc.nextInt();
                if (j > i && cost != 0) {
                    Edge edge = new Edge(i, j, cost);
                    allEdges.add(edge);
                    node.addEdge(edge);
                }
                if (j < i && cost != 0) {
                    node.addEdge(new Edge(i, j, cost));
                }
            }
            nodes.add(node);

            connectedCmps[i] = new HashSet(numNodes);
            connectedCmps[i].add(i);
        }


        int numSubgraph = sc.nextInt();
        A = new ArrayList<Integer>(numSubgraph);
        for (int i = 0; i < numSubgraph; i++) {
            A.add(sc.nextInt() - 1);
        }

        K = sc.nextInt();

        spanningTree = new ArrayList<Edge>(numNodes - 1);
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

    private static void minimumSpanningTree() {
        int sizeEdges = allEdges.size();
        TreeSet<Edge> edges = (TreeSet<Edge>) allEdges.clone();
        for (int i = 0; i < sizeEdges; i++) {
            Edge edge = (Edge) edges.first();
            edges.remove(edge);

            // nodes are not in different sets, therefore this edge would cause a cycle
            if (connectedCmps[edge.from] == connectedCmps[edge.to]) {
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
            spanningTree.add(edge);
        }
    }

    private static void reduceSpanningTree() {
        reducedSpanningTree = new ArrayList<Edge>();
        reducedEdges = (TreeSet<Edge>) allEdges.clone();
        for (int i = 0; i < nodes.size(); i++) { // clear connectedCmps for reuse
            connectedCmps[i] = new HashSet(nodes.size());
            connectedCmps[i].add(i);
        }

        for (Edge edge : spanningTree) {
            if (A.contains(edge.from) || A.contains(edge.to)) {
                continue;
            }

            reducedSpanningTree.add(edge);
            reducedEdges.remove(edge);

            // merge connected components together and update all relevant nodes
            HashSet<Integer> from = connectedCmps[edge.from], to = connectedCmps[edge.to];
            for (Integer node : from) {
                to.add(node);
            }
            for (Integer node : to) {
                connectedCmps[node] = to;
            }
        }
    }
    private static ArrayList<Edge> rst;
    private static HashSet<Integer> ccmp[];
    private static TreeSet<Edge> re;

    private static int getHedgehogSpanningTreeCost(List<Integer> indices) {
        // clone for each new hedgehog spanning tree
        rst = (ArrayList<Edge>) reducedSpanningTree.clone();
        ccmp = new HashSet[nodes.size()];
        for (int i = 0; i<connectedCmps.length; i++) {
            ccmp[i] = (HashSet<Integer>) connectedCmps[i].clone();
        }
        re = (TreeSet<Edge>) reducedEdges.clone();
        thisCombinationCost = 0;

        // prepare set B
        HashSet<Integer> B = new HashSet<Integer>();
        for (int i = 0; i < indices.size(); i++) {
            B.add(A.get(indices.get(i)));
        }

        // Add A\B in the cheapest way
        if(hedgeHogAddANodesWithoutBNodes(indices, B) == -1) {
            return -1;
        };

       // Connect connected components
        if(connectConnectedComponents(B) == -1) {
            return -1;
        }

        // add B nodes as needles and make sure that B nodes are not added onto themselves
        for (Integer index : B) {
            Edge edge = getCheapestEdgeNotBetweenB(nodes.get(index), B);
            if(edge == null) {
                return -1;
            }
            if(!addRSTEdge(edge)) {
                return -1;
            }
        }
        return thisCombinationCost;
    }

    private static Edge getCheapestEdgeNotPointingToB(Node node, HashSet<Integer> B) {
        TreeSet<Edge> nodeEdges = (TreeSet<Edge>) node.getEdges().clone();
        Edge edge = null;
        while (true) {
            if (nodeEdges.size() < 1) {
                // there is no such edge from this node
                return null;
            }
            edge = nodeEdges.first();
            if (B.contains(edge.from) || B.contains(edge.to)) {
                nodeEdges.remove(edge);
            } else { // we found the cheapest edge
                return edge;
            }
        }
    }

    private static Edge getCheapestEdgeNotBetweenB(Node node, HashSet<Integer> B) {
        TreeSet<Edge> nodeEdges = (TreeSet<Edge>) node.getEdges().clone();
        Edge edge = null;
        while (true) {
            if (nodeEdges.size() < 1) {
                // there is no such edge from this node
                return null;
            }
            edge = nodeEdges.first();

            if (B.contains(edge.from) && B.contains(edge.to)) {
                nodeEdges.remove(edge);
            } else { // we found the cheapest edge
                return edge;
            }
        }
    }

    private static int hedgeHogAddANodesWithoutBNodes(List<Integer> indices, HashSet<Integer> B) {
        for (int i = 0; i < A.size(); i++) {
            if (indices.contains(i)) { // skip over the B set
                continue;
            }
            // take A node edges and find the cheapest edge that doesn't connect to B node
            Edge edge = getCheapestEdgeNotPointingToB(nodes.get(A.get(i)), B);
            // this node could not be added to the graph without connecting to B
            // therefore this combination is not feasible
            if(edge == null) {
                return -1;
            }

            // this edge is alredy used, therefore A node is connected to our graph
            if (rst.contains(edge)) {
                //System.out.println("RST already contains edge "+edge);
                continue;
            }

            // this edge is not used yet, add A node to the graph and update connected components+
            HashSet<Integer> from = ccmp[edge.from], to = ccmp[edge.to];
            for (Integer nodeIdx : from) {
                to.add(nodeIdx);
            }
            for (Integer nodeIdx : to) {
                ccmp[nodeIdx] = to;
            }
            if(!addRSTEdge(edge)) {
                return -1;
            }
            re.remove(edge);
        }
        return 0;
    }

    private static int connectConnectedComponents(HashSet<Integer> B) {
        int sizeEdges = re.size();
        int biggestConnectedCmp = 0;
        for (int i = 0; i < sizeEdges; i++) {
            Edge edge = re.first();
            re.remove(edge);

            // skip B nodes
            if (B.contains(edge.from) || B.contains(edge.to)) {
                continue;
            }
            // nodes are not in different sets, therefore this edge would cause a cycle
            if (ccmp[edge.from] == ccmp[edge.to]) {
                continue;
            }

            // merge connected components together and update all relevant nodes
            HashSet<Integer> from = ccmp[edge.from], to = ccmp[edge.to];
            for (Integer node : from) {
                to.add(node);
            }
            for (Integer node : to) {
                ccmp[node] = to;
            }
            if (to.size() > biggestConnectedCmp) {
                biggestConnectedCmp = to.size();
            }
            if(!addRSTEdge(edge)) {
                return -1;
            }
        }
        // could not connect connected components into a big one
        // therefore this combination is not possible to realize
        if (biggestConnectedCmp < nodes.size() - K) {
            return -1;
        }

        return 0;
    }

    private static boolean addRSTEdge(Edge edge) {
        if(thisCombinationCost+edge.cost > lowestCost) {
            return false;
        }
        rst.add(edge);
        thisCombinationCost += edge.cost;
        return true;
    }
}
