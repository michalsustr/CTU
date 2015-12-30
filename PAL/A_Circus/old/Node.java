/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author michal
 */
public class Node implements Comparable {
    public ArrayList<Node> neighbours;
    public int index; // from input
    public int price = 0, profit = 0;
    public int label = -1; // for tarjan
    public int lowlink; // for tarjan
    public Region region;
    // 0 for no visited town, 1 for two visited towns, others for 1 visited town and its index
    public int[] ranks;
    public int regionIndex; // index for ranks in region

    public Node(int index, int price, int profit) {
        this.index = index;
        this.price = price;
        this.profit = profit;
        this.neighbours = new ArrayList<Node>();
    }

    public void prepareRanks(int regionTowns) {
        ranks = new int[regionTowns+2];
        for (int i = 0; i < regionTowns+2; i++) {
            ranks[i] = Integer.MIN_VALUE;
        }
    }

    public boolean isTown() {
        return this.profit != 0;
    }

    public void playTown(int rankIndex) {
        this.ranks[rankIndex] = this.ranks[rankIndex] == Integer.MIN_VALUE
                    ? (this.profit)
                    : (this.ranks[rankIndex] + this.profit);
    }

    public void payPrice(int rankIndex) {
        this.ranks[rankIndex] = this.ranks[rankIndex] == Integer.MIN_VALUE
                    ? (-this.price)
                    : (this.ranks[rankIndex] - this.price);
    }

    public int maxRank() {
        Integer max = Integer.MIN_VALUE;
        for (int i = 0; i < ranks.length; i++) {
            if(ranks[i] > max) {
                max = ranks[i];
            }
        }
        return max;
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    boolean isProfitable() {
        return profit > price;
    }

    public String printRank() {
        String ret = "";
        for (int i = 0; i < ranks.length; i++) {
            ret = ret+"\t"+ (ranks[i] == Integer.MIN_VALUE ? "-" : ranks[i]);
        }
        return ret;
    }

    @Override
    public String toString() {
        return ""+index;
    }

    @Override
    public int compareTo(Object o) {
        Node other = (Node) o;
        if (this.index < other.index) {
            return -1;
        } else if (this.index > other.index) {
            return 1;
        } else {
            return 0;
        }

    }

    int regionNeighbours() {
        int rn = 0;
        for(Node n : neighbours) {
            if(n.region.index == this.region.index) rn++;
        }
        return rn;
    }

}
