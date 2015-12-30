/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author michal
 */
public class Region {
    public ArrayList<Node> nodes;
    public int topo;

    public Region(int topo) {
        this.topo = topo;
        this.nodes = new ArrayList<Node>();
    }

    public void add(Node n) {
        n.region = this;
        nodes.add(n);
    }

    public void searchRegion() {
        LinkedList<Item> open = new LinkedList<>();

        // if there are no starting nodes, open with towns
        for (Node n : nodes) {
            if(n.isVillage() && n.maxBeforeEntering-n.price <= 0) {
                continue;
            }

            open.addLast(new Item( n, n.maxBeforeEntering, null, null));
        }

        int valueVisitingNode = 0;
        while(!open.isEmpty()) {
            Item i = open.poll();

            // extract variables
            Node n  = i.currentNode;
            Node t1 = i.playedInTown1;
            Node t2 = i.playedInTown2;
            int  v  = i.currentValue;
            int timesPlayed = i.timesPlayed;

            if(v < n.maxBeforeEntering) {
                continue;
            }
            n.maxBeforeEntering = v;

            switch(timesPlayed) {
                case 0:
                    // play in the town
                    if(n.isTown()) {
                        valueVisitingNode = v + n.cashflow;
                        t1 = n;
                        timesPlayed++;
                    } else {
                    // pass the village
                        valueVisitingNode = v - n.price;
                    }
                    break;

                case 1:
                    if(n.isTown()) {
                        // do not play in the same town again
                        if(n != t1) {
                            valueVisitingNode = v + n.cashflow;
                            t2 = n;
                            timesPlayed++;
                        } else {
                            // only pass the town
                            valueVisitingNode = v - n.price;
                        }
                    } else { // Village.
                        valueVisitingNode = v - n.price;
                    }
                    break;

                case 2:
                    if(n.isTown()) {
                        if(n != t1 && n != t2) {
                            if(t1.cashflow < t2.cashflow) {
                                if(t1.cashflow < n.cashflow) {
                                    // update the town to be playing in current node, not in t1
                                    valueVisitingNode = v - t1.profit + n.cashflow;
                                    t1 = n;
                                }
                            } else {
                                // t2 >= t1
                                if(t2.cashflow < n.cashflow) {
                                    // update the town to be playing in current node, not in t2
                                    valueVisitingNode = v - t2.profit + n.cashflow;
                                    t2 = n;
                                }
                            }
                        } else {
                            // only pass the previously played town
                            valueVisitingNode = v-n.price;
                        }
                    } else { // pass village
                        valueVisitingNode = v - n.price;
                    }
                    break;
            }


            // we have not achieved a better value, so skip this investigation
            if(valueVisitingNode <= n.maxAfterLeaving) {
                continue;
            }
            n.maxAfterLeaving = valueVisitingNode;

            // update global max
            if(n.maxAfterLeaving > Main.maxProfit) {
                Main.maxProfit = n.maxAfterLeaving;
            }

            // expand to neighbours
            for(Node next : n.neighbours) {
                if(next.region.topo == n.region.topo) { // The point is in this region.
                    if(next.isVillage() && valueVisitingNode-next.price <= 0) {
                        continue;
                    }

                    if(valueVisitingNode > next.maxBeforeEntering) {
                        open.add(new Item(next, valueVisitingNode, t1, t2));
                    }
                } else { // The point isn't in this region.
                    if(valueVisitingNode > next.maxBeforeEntering) {
                        next.maxBeforeEntering = valueVisitingNode;
                    }
                }
            }
        }
    }

    void printRegion() {
        System.err.println("## Region " + topo);
        for (Node n : nodes) {
            System.err.println("->Node " + n.index + " label:" + n.label + " lowlink:" + n.lowlink);
        }
        System.err.println("");
    }
}
