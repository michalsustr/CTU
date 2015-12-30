/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author pc404
 */
public class Main {
    private static int N;
    private static Node[] nodes;
    private static LinkedList<Item> open;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        loadInput();
        
        // find the roots of the graph
        open = new LinkedList<Item>();
        for (int i = 0; i < N; i++) {
            if(nodes[i].isStarting()) {
                Item list = new Item();
                list.add(nodes[i]);
                open.add(list);
            }
        }
        
        dfs();
        
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for(Integer v : nodes[i].edges.values()) {
                if(v > 1) sum+=v;
            }
        }
        
        System.out.println(sum);
    }
    
    public static void dfs() {
        while(!open.isEmpty()) {
            Item item = open.poll();
            Node n = item.path.getLast();
            // expand to neighbours
            for(Node next : n.edges.keySet()) {
                // lookup the list if the node has been visited
                if(item.expands.containsKey(next)) {
                    int idx = item.expands.get(next);
                    HashMap<Node, Integer> ne = item.path.get(idx).edges;

                    if(ne.get(next) > item.path.size()-idx) {
                        ne.put(next, item.path.size()-idx);
                    }
                }
                
                if(n.edges.get(next) == 1) {
                    Item newWave = item.copy();
                    newWave.add(next);
                    open.add(newWave);
                }
            }
        }
    }
    
    
    
    private static void loadInput() throws IOException {
        // load input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        
        for (int i = 0; i < N; i++) {
            nodes[i] = new Node();
        }
        
        int node1, node2, k;
        String line;
        while (true) {
            line = br.readLine() + " ";      // add sentinel
            k = 0;
            node1 = 0;
            node2 = 0;

            while (line.charAt(k) != ' ') { // get first node
                node1 = node1 * 10 + line.charAt(k++) - 48;
            }

            while (line.charAt(++k) != ' ') {// get second node
                node2 = node2 * 10 + line.charAt(k) - 48;
            }

            if (node1 + node2 == 0) {
                break;
            }

            nodes[node1].addEdge(nodes[node2]);
            nodes[node2].setHasParent();
        }
    }

    private static class Node {
        
        public HashMap<Node, Integer> edges;
        public boolean hasParent = false;

        public Node() {
            edges = new HashMap<Node, Integer>();
        }
        
        public void setHasParent() {
            hasParent=true;
        }
        
        public boolean isStarting() {
            return hasParent == false && edges.size() > 0;
        }
        
        public void addEdge(Node n) {
            edges.put(n, 1);
        }
        
        
    }

    private static class Item {
        public LinkedList<Node> path;
        public HashMap<Node, Integer> expands;
        public Item() {
            path = new LinkedList<Node>();
            expands = new HashMap<Node,Integer>();
        }
        
        public void add(Node n) {
            path.add(n);
            for(Node next : n.edges.keySet()) {
                if(!expands.containsKey(next)) {
                    expands.put(next, path.size()-1);
                }
            }
        }
        
        public Item copy() {
            Item copy = new Item();
            copy.path.addAll(path);
            copy.expands.putAll(expands);
            return copy;
        }
    }
}
