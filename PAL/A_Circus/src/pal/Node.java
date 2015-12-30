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
public class Node {
    public ArrayList<Node> neighbours;

    public int index; // from input
    public int price = 0, profit = 0, cashflow = 0;

    public int label = -1; // for tarjan
    public int lowlink;    // for tarjan

    public Region region;

    public int maxBeforeEntering = 0, maxAfterLeaving = 0;

    public Node(int index, int price, int profit) {
        this.index = index;
        this.price = price;
        this.profit = profit;
        this.cashflow = this.profit - this.price;
        this.neighbours = new ArrayList<Node>();
    }

    public boolean isTown() {
        return this.profit != 0;
    }

    public boolean isVillage() {
        return this.profit == 0;
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    boolean isProfitable() {
        return cashflow > 0;
    }

    @Override
    public String toString() {
        return ""+index;
    }
}
