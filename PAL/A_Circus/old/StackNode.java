/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

/**
 *
 * @author michal
 */
public class StackNode {
    public Node n;
    public boolean played;

    public StackNode(Node n, boolean played) {
        this.n = n;
        this.played = played;
    }

    @Override
    public String toString() {
        if(n.isTown()) {
            return ""+n.index+""+(played ? "play" : "pass");
        }
        return ""+n.index;
    }




}
