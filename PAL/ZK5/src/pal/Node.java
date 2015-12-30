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
   Node parent;
   int id;
   ArrayList<Node> neighbours;

   boolean visited;

    public Node() {
        this.neighbours = new ArrayList<Node>();
    }


}
