/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class Node implements Comparable {
    public ArrayList<Node> neighbours;

    public int index; // from input
    public int price = 0, profit = 0;

    public int label = -1; // for tarjan
    public int lowlink;    // for tarjan

    public Region region;

    // 0 for no visited town, 1 for two visited towns, others for 1 visited town and its index
    public int townIndex; // index for ranks in region (2nd dim of ranks array)
    public int nodeIndex; // index for ranks in region (1st dim of ranks array)

    public Node(int index, int price, int profit) {
        this.index = index;
        this.price = price;
        this.profit = profit;
        this.neighbours = new ArrayList<Node>();
    }

    public boolean isTown() {
        return this.profit != 0;
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
            if(n.region.topo == this.region.topo) rn++;
        }
        return rn;
    }

}
