/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.util.HashMap;

/**
 *
 * @author michal
 */
public class Node {
    public HashMap<Integer, Node> nodes;
    public static int cnt = 0;

    public Node() {
        nodes = new HashMap<Integer, Node>();
    }
    public void addWord(int[] w, int index) {
        if(!nodes.containsKey(w[index])) {
            nodes.put(w[index], new Node());
            cnt++;
        }
        if(index+1 < w.length) {
            nodes.get(w[index]).addWord(w, index+1);
        }
    }
}
