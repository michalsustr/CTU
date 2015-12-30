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
public class Item {
    public int nodeIndex;
    public int townIndex;
    public Stack depthStack;

    public Item(int nodeIndex, int townIndex, Stack depthStack) {
        this.nodeIndex = nodeIndex;
        this.townIndex = townIndex;
        this.depthStack = depthStack;
    }
}
