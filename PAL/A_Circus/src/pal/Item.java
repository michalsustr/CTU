/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

/**
 *
 * @author michal
 */
public class Item {
    public Node currentNode;
    public Node playedInTown1, playedInTown2;
    public int timesPlayed = 0;
    public int currentValue;

    public Item(Node currentNode, int currentValue, Node town1, Node town2) {
        this.currentNode = currentNode;
        this.playedInTown1 = town1;
        if(town1 != null) timesPlayed++;
        this.playedInTown2 = town2;
        if(town2 != null) timesPlayed++;
        this.currentValue = currentValue;
    }
}
