/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.util.Stack;

/**
 *
 * @author michal
 */
public class OpenItem {
    public Node current;
    public Node prev;
    public Node town1;
    public Node town2;
    public int numTowns;
    public int rankIndex;
    public static int wave = 0;
//    public int depth;
    public boolean in;
//    public Stack depthStack;

    public OpenItem(Node current, Node prev, Node town1, Node town2, boolean in /*, int depth, Stack depthStack */) {
        wave++;
        this.current = current;
        this.prev = prev;
        this.town1 = town1;
        this.town2 = town2;
        this.numTowns = 0;
//        this.depth = depth;
//        this.depthStack = depthStack;
        this.in = in;
        if(town1 != null) this.numTowns++;
        if(town2 != null) this.numTowns++;
        this.rankIndex = this.numTowns == 1 ? town1.regionIndex :
                this.numTowns == 2 ? 1 : 0;
    }

    boolean play() {
        // play the town
//        System.err.println("Playing #towns:"+numTowns+" T1:"+town1+" T2:"+town2);
        boolean wasPlayed = false;

        if(numTowns == 0) {
            assert this.town1 == null;
            this.town1 = current;

            // propagate rank
            if(current.ranks[town1.regionIndex] < current.ranks[0]) {
               current.ranks[town1.regionIndex] = current.ranks[0];
            }

            rankIndex = current.regionIndex;
            numTowns = 1;
            current.playTown(rankIndex);
            current.payPrice(rankIndex);
            wasPlayed = true;
        } else {
            assert numTowns == 1;
            assert this.town2 == null;
            this.town2 = current;

            if(prev.ranks[rankIndex]-current.price+current.profit > current.ranks[1]) {
                // propagate rank
                if(current.ranks[town1.regionIndex] != Integer.MIN_VALUE) {
                    current.ranks[1] = current.ranks[town1.regionIndex];
                }
                numTowns = 2;
                rankIndex = 1;
                current.playTown(rankIndex);
                current.payPrice(rankIndex);
                wasPlayed = true;
            }
        }


        // there is no need for investigation of towns, we have found first one
        if(in) {
            current.region.addInvestigationOfTowns = false;
        }

        // update max rank
        int maxRank = current.maxRank();
        if (maxRank > Main.maxProfit) {
            Main.maxProfit = maxRank;
            //System.err.println("\tNew max"+maxRank);
        }

        return wasPlayed;
    }

    void pass() {
        current.payPrice(rankIndex);
    }
}
