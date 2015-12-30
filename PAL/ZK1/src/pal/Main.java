/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 *
 * @author michal
 */
public class Main {
    private static int N;
    private static Node[] nodes;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        loadInput();

        HashSet<Integer> trees = new HashSet<Integer>();

        for (int i = 0; i < N; i++) {
            trees.add(nodes[i].cert());
        }
        System.out.println(trees.size()+1);
    }

    private static void loadInput() {
        // load input
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        nodes = new Node[N];
        for (int i = 0; i < N; i++) {
            nodes[i] = new Node();
        }
        for (int i = 0; i < N-1; i++) {
            int N1 = sc.nextInt();
            int D = sc.nextInt();
            int N2 = sc.nextInt();
            int W = sc.nextInt();

            nodes[N1].add(nodes[N2], D, W);
        }
    }

    private static class Node {
        Node left;
        int leftW;
        Node right;
        int rightW;
        Integer cert;
        public Node() {
        }

        void add(Node node, int D, int W) {
            if(D == 0) {
                left = node;
                leftW = W;
            } else {
                right = node;
                rightW = W;
            }
        }

        Integer cert() {
            if(cert != null) {
                return cert;
            }

            if(left == null && right == null) {
                cert = 0;
                return cert;
            }
            if(left == null && right != null) {
                cert=20*rightW+right.cert();
                return cert;
            }
            if(left != null && right == null) {
                cert =20*leftW+left.cert();
                return cert;
            }
            // else left and right are set
            if(leftW < rightW) {
                cert = 100*leftW+left.cert()+200*rightW+right.cert();
                return cert;
            } else {
                cert=100*rightW+right.cert()+200*leftW+left.cert();
                return cert;
            }
        }
    }
}
