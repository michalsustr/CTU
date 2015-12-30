/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michal
 */
public class Region {
    public ArrayList<Node> nodes;
    public int topo;

    public int townIndex = 2; // so that we skip the first two values in ranks
    public int nodeIndex = 0;

    LinkedList<Item> open;
    public int[][] ranks;

    public Region(int topo) {
        this.topo = topo;
        this.nodes = new ArrayList<Node>();
        this.open = new LinkedList<Item>();
    }

    public void add(Node n) {
        n.nodeIndex = nodeIndex++;
        n.region = this;
        if (n.isTown()) {
            n.townIndex = townIndex++;
        }

        nodes.add(n.nodeIndex, n);
    }

    public boolean isProfitable() {
        boolean profitable = false;
        for (Node node : nodes) {
            if (node.isTown() && node.isProfitable()) {
                profitable = true;
            }
        }
        return profitable;
    }

    public void init() {
        // initialize ranks table
        ranks = new int[nodes.size()][townIndex];
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < ranks[i].length; j++) {
                ranks[i][j] = Integer.MIN_VALUE;
            }
        }
    }

    public void searchRegion() {
        // if there are no starting nodes, open with towns
        boolean enableRegionRestart = true;
        if (open.size() == 0) {
            for (Node n : nodes) {
                if(n.isTown()) {
                    open.addLast(new Item(n.index,0, new Stack()));
                }
            }
            enableRegionRestart = false;
        }

writeRegion();
System.err.println("== Region "+topo);

        while(!open.isEmpty()) {
            Item i = open.poll();
            int n = i.nodeIndex;
            int t = i.townIndex;

System.err.print("Node: "+nodes.get(n).index+" ");
if(i.depthStack.size() > 0 && nodes.get(n).index == ((StackNode) i.depthStack.peek()).n.index) {
    StackNode sn = (StackNode) i.depthStack.pop();
    System.err.println(i.depthStack.toString());
    i.depthStack.add(sn);
} else {
    System.err.println(i.depthStack.toString());
}



            // we have travelled two towns, stop from propagating negative values
//            if(t == 1 && ranks[n][1] != Integer.MIN_VALUE && ranks[n][1] < 0) {
//                continue;
//            }

            // we have not yet visited two towns
            if (nodes.get(n).isTown() && t != 1) {
System.err.println("\tPlaying town");
                Item newWave = play(i);
                if(newWave != null) {
                    bfsNeighbours(newWave);
                }

                // pass the town
                if(ranks[n][t]-nodes.get(n).price > 0) {
System.err.println("\tPassing town");
                    i.depthStack = getNewDepthStack(i.depthStack, nodes.get(n), false);
                    pass(i);
                    bfsNeighbours(i);
                }
            } else {
                // pass the village or double visited town
                if(ranks[n][t]-nodes.get(n).price > 0) {
System.err.println("\tPassing node");
                    pass(i);
                    bfsNeighbours(i);
                }
            }

            if(open.isEmpty() && enableRegionRestart) {
                enableRegionRestart = false;
System.err.println("\tRestarting region with towns");
                for (Node node : nodes) {
                    if(node.isTown()) {
System.err.println("  "+node.index);
                        open.addLast(new Item( node.nodeIndex, 0, new Stack()));
                    }
                }
            }

writeNodes();
System.err.println("");
        }
    }

    private void bfsNeighbours(Item i) {
        // expand to other nodes
        int n = i.nodeIndex;
        int t = i.townIndex;
        Node current = nodes.get(n);

        assert ranks[n][t] != Integer.MIN_VALUE;

        for (Node next : current.getNeighbours()) {
            int m = next.nodeIndex;
            // an edge going away from this region
            if (next.region.topo != current.region.topo) {
                if (ranks[n][t] > next.region.ranks[m][0]) {
                    if(next.region.ranks[m][0] == Integer.MIN_VALUE) {
                        next.region.open.addLast(new Item(m, 0, new Stack()));
                    }
                    next.region.ranks[m][0] = ranks[n][t];
                }
            } else {
                if(ranks[n][t]-next.price+next.profit > next.region.ranks[m][t]) {
                    // do not apply "+next.profit" this for the towns that have been visited twice
                    if(t == 1 && ranks[n][t]-next.price <= next.region.ranks[m][t]) {
                        continue;
                    }
                    next.region.ranks[m][t] = ranks[n][t];
                    open.addLast(new Item(m, t, i.depthStack));
                }
            }
        }
    }

    Item play(Item i) {
        int n = i.nodeIndex;
        int t = i.townIndex;

        Node current = nodes.get(n);
        int c = current.townIndex;

        assert t != 1;

        Item newWave = null;

        // playing first town
        if(t == 0) {
            // propagate rank
            if(ranks[n][0] > ranks[n][c]) {
               ranks[n][c] = ranks[n][0];
            }

            playTown(n,c);
            payPrice(n,c);

            newWave = new Item(n, c, getNewDepthStack(i.depthStack, current, true));
        } else {
        // playing second town
            assert t > 1;

            if(ranks[n][c]-current.price+current.profit > ranks[n][1]) {
                // propagate rank
                ranks[n][1] = ranks[n][c];

                playTown(n,1);
                playTown(n,1);

                newWave = new Item(n, 1, getNewDepthStack(i.depthStack, current, true));
            }
        }

        // update max rank
        int maxRank = maxRank(n);
        if (maxRank > Main.maxProfit) {
            Main.maxProfit = maxRank;
System.err.println("\tNew max"+maxRank);
        }

        return newWave;
    }

    void pass(Item i) {
        payPrice(i.nodeIndex, i.townIndex);
    }

    public void playTown(int n, int t) {
        int profit = nodes.get(n).profit;
        ranks[n][t] = ranks[n][t] == Integer.MIN_VALUE
                    ? (profit)
                    : (ranks[n][t] + profit);
    }
    public void payPrice(int n, int t) {
        int price = nodes.get(n).price;
        ranks[n][t] = ranks[n][t] == Integer.MIN_VALUE
                    ? (-price)
                    : (ranks[n][t] - price);
    }

    public int maxRank(int n) {
        Integer max = Integer.MIN_VALUE;
        for (int i = 0; i < ranks[n].length; i++) {
            if(ranks[n][i] > max) {
                max = ranks[n][i];
            }
        }
        return max;
    }

    void printRegion() {
        System.err.println("## Region " + topo);
        for (Node n : nodes) {
            System.err.println("->Node " + n.index + " label:" + n.label + " lowlink:" + n.lowlink);
        }
        System.err.println("");
    }
    static int openIndex = 0;
    void writeNodes() {
        openIndex++;
        System.err.println("\tInvestigation #"+openIndex);

        StringBuilder bd = new StringBuilder();

        bd.append("Region #"+topo+"\n");
        bd.append("Node\tT0\tT1");
        for (int i = 0; i < nodes.size(); i++) {
            if(!nodes.get(i).isTown()) continue;

            bd.append("\tN");
            for (int j = 0; j < nodes.size(); j++) {
                if(!nodes.get(i).isTown()) continue;

                if(nodes.get(j).townIndex-2 == i) {
                    bd.append(""+nodes.get(j).index);
                }
            }
        }
        bd.append("\n");

        for (int n = 0; n < ranks.length; n++) {
            bd.append(nodes.get(n).index+"\t");
            for (int t = 0; t < ranks[n].length; t++) {
                bd.append((ranks[n][t] == Integer.MIN_VALUE ? "-" : ranks[n][t]) +"\t");
            }
            bd.append("\n");
        }

        try {
            File file = new File("./out/"+openIndex+".txt");
             // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(bd.toString());
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Region.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void writeRegion() {
        StringBuilder bd = new StringBuilder();

        bd.append("Region "+topo+ " with nodes: \n");
        for(Node n : nodes) {
            bd.append("  #"+n.townIndex+" has index "+n.index+"\n");
        }
        bd.append("Start nodes:\n");
        for(Item i : open) {
            bd.append("  "+nodes.get(i.nodeIndex).index+" with rank "+(ranks[i.nodeIndex][0] == Integer.MIN_VALUE ? "-" : ranks[i.nodeIndex][0])+"\n");
        }
        bd.append("OpenIndex: "+openIndex);


        try {
            File file = new File("./out/reg"+topo+".txt");
             // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(bd.toString());
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Region.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Stack getNewDepthStack(Stack depthStack, Node n, boolean b) {
        Stack newStack = new Stack();
        newStack.addAll(depthStack);
        if(newStack.size() > 0) {
            StackNode sn = (StackNode) newStack.pop();
            newStack.add(new StackNode(sn.n, b));
        } else {
            newStack.add(new StackNode(n, b));
        }
        return newStack;
    }
}
