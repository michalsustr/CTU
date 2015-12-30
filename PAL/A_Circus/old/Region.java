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
    public ArrayList<Node> towns;
    public int index;
    public int nodeIndex = 2; // so that we skip the first two values in ranks

    public Region(int index) {
        this.index = index;
        nodes = new ArrayList<Node>();
        towns = new ArrayList<Node>();
        open = new LinkedList<OpenItem>();
    }

    public void add(Node n) {
        nodes.add(n);
        n.region = this;
        if (n.isTown()) {
            n.regionIndex = nodeIndex++;
            towns.add(n);
        }
    }

    public boolean isProfitable() {
        boolean profitable = false;
        for (Node town : towns) {
            if (town.isProfitable()) {
                profitable = true;
            }
        }
        return profitable;
    }

    LinkedList<OpenItem> open;
    boolean addInvestigationOfTowns = true;
    static int localDepth = 0;
    static int openIndex = 0;
    public void searchRegion() {
        // if there are no starting nodes, open with towns
        if (open.size() == 0) {
            for (Node t : towns) {
                open.addLast(new OpenItem(t,null,null,null, false /*, 0, new Stack() */));
            }
            addInvestigationOfTowns = false;
        }
        //writeRegion();
        //System.err.println("== Region "+index);

        while(!open.isEmpty()) {
            OpenItem i = open.poll();
            Node current = i.current;
            int numTowns = i.numTowns;

            // debug
            /*// <editor-fold>
                if(i.depth > localDepth) {
                    localDepth = i.depth;
                }

                System.err.print("Node: "+i.current.index+" ");
                if(i.depthStack.size() > 0 && i.current.index == ((StackNode) i.depthStack.peek()).n.index) {
                    StackNode sn = (StackNode) i.depthStack.pop();
                    System.err.println(i.depthStack.toString());
                    i.depthStack.add(sn);
                } else {
                    System.err.println(i.depthStack.toString());
                }
            // </editor-fold> */

            boolean canInvestigatePassingTown = true;
            boolean doingSmthInTown = false; // debug var

            // stop from propagating
            if(numTowns == 2 && current.ranks[1] != Integer.MIN_VALUE && current.ranks[1] < 0) {
                continue;
            }

            if (current.isTown() && numTowns < 2) {
                // make sure that town1 would not become also town2, we don't pass the same town twice to play!
                if (i.town1 == null || (i.town1 != null && i.town1.index != current.index)) {

                    if (numTowns == 0) {
                        if(current.ranks[0] != Integer.MIN_VALUE) {
                            // restart playing
                            if(current.ranks[0] < 0) {
                                assert 0 == 1;
                                //System.err.println("\tRestart playing");
                                current.ranks[0] = 0;
                                canInvestigatePassingTown = false;
                            }
                        } else {
                            canInvestigatePassingTown = false;
                        }

                    }

                    // play the town
                    doingSmthInTown = true;
                    //System.err.println("\tPlaying town");
                    // new wave
//                    Stack depthStack = getNewDepthStack(i.depthStack, i.current, true);
                    OpenItem passOpenItem = new OpenItem(i.current, i.prev, i.town1, i.town2, i.in /*, i.depth, depthStack*/);
                    if(passOpenItem.play()) {
                        bfsNeighbours(passOpenItem);
                    }
                    // pass the town
                    if(canInvestigatePassingTown) {
                        if(current.ranks[i.rankIndex]-current.price > 0) {
                            doingSmthInTown = true;
                            //System.err.println("\tPassing town");
                            i.pass();
//                            i.depthStack = getNewDepthStack(i.depthStack, i.current, false);
                            bfsNeighbours(i);
                        }
                    }


                }
            } else {
                // pass the village or town if rankIndex == 2
                if(current.ranks[i.rankIndex]-current.price > 0) {
                    doingSmthInTown = true;
                    //System.err.println("\tPassing node");
                    i.pass();
                    bfsNeighbours(i);
                } else {
                    //System.err.println("");
                    //System.err.println("That's why :-)");
                }
            }
            if(!doingSmthInTown) {
                //System.err.println("\tNot doing anything");
            }

            if(open.isEmpty() && addInvestigationOfTowns) {
                addInvestigationOfTowns = false;
                //System.err.println("\tRestarting region with towns");
                for (Node t : towns) {
                    //System.err.println("  "+t.index);
                    open.addLast(new OpenItem(t,null,null,null, false /*, 0, new Stack()*/));
                }
            }

            //writeNodes();
            //System.err.println("");
        }
    }

    private void bfsNeighbours(OpenItem i) {
        // expand to other nodes
        Node current = i.current;
        assert current.ranks[i.rankIndex] != Integer.MIN_VALUE;

        for (Node next : current.getNeighbours()) {
            if (next.region.index != current.region.index) {
                if (current.ranks[i.rankIndex] > next.ranks[0]) {
                    //System.err.println("\tPropagating to region "+next.region.index+" to node "+next.index+" with rank "+current.ranks[i.rankIndex]);
                    if(next.ranks[0] == Integer.MIN_VALUE) {
                        next.region.open.addLast(new OpenItem(next, current, null, null, true /*, i.depth+1, new Stack()*/));
                    }
                    next.ranks[0] = current.ranks[i.rankIndex];
                }
            } else {
                if(current.ranks[i.rankIndex]-next.price+next.profit > next.ranks[i.rankIndex]) {
                    if(i.numTowns == 2 && current.ranks[i.rankIndex]-next.price <= next.ranks[i.rankIndex]) {
                        continue;
                    }
                    next.ranks[i.rankIndex] = current.ranks[i.rankIndex];
//                    Stack depthStack = new Stack();
//                    depthStack.addAll(i.depthStack);
//                    if(next.isTown() || next.regionNeighbours() > 1) {
//                        depthStack.add(new StackNode(next, false));
//                    }
                    open.addLast(new OpenItem(next, current, i.town1, i.town2, i.in/*, i.depth+1, depthStack*/));
                }
            }
        }
    }

    void printRegion() {
        System.err.println("## Region " + index);
        for (Node n : nodes) {
            System.err.println("->Node " + n.index + " label:" + n.label + " lowlink:" + n.lowlink);
        }
        System.err.println("");
    }

    void writeNodes() {
        openIndex++;
        System.err.println("\tInvestigation #"+openIndex);

        StringBuilder bd = new StringBuilder();

        bd.append("Region #"+index+"\n");
        bd.append("Node\tT0\tT1");
        for (int i = 0; i < towns.size(); i++) {
            bd.append("\tN");
            for (int j = 0; j < towns.size(); j++) {
                if(towns.get(j).regionIndex-2 == i) {
                    bd.append(""+towns.get(j).index);
                }
            }
        }
        bd.append("\n");
        Collections.sort(nodes);
        for (Node n : nodes) {
            bd.append(n.index+"\t");
            for (int i = 0; i < n.ranks.length; i++) {
                bd.append((n.ranks[i] == Integer.MIN_VALUE ? "-" : n.ranks[i]) +"\t");
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

        bd.append("Region "+index+ " with nodes: \n");
        for(Node n : nodes) {
            bd.append("  #"+n.regionIndex+" has index "+n.index+"\n");
        }
        bd.append("Start nodes:\n");
        for(OpenItem i : open) {
            bd.append("  "+i.current.index+" with rank "+(i.current.ranks[0] == Integer.MIN_VALUE ? "-" : i.current.ranks[0])+"\n");
        }
        bd.append("OpenIndex: "+openIndex);


        try {
            File file = new File("./out/reg"+index+".txt");
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
