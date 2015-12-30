/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author michal
 */
public class Main {
    private static Integer[] minLDRow;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // load input
        Scanner sc = new Scanner(new BufferedInputStream(System.in));

        int N, M, A1, A2, B, C, D, E;
        N = sc.nextInt();
        M = sc.nextInt();
        A1 = sc.nextInt();
        A2 = sc.nextInt();
        B = sc.nextInt();
        C = sc.nextInt();
        D = sc.nextInt();
        E = sc.nextInt();
        int[] t = new int[N];
        int[] p = new int[M];

        // Generate text
        long Fi_prev = A1, Fi;
        t[0] = A1 % E;
        for (int i = 1; i < N; i++) {
            Fi = ((Fi_prev+1)*B+C) % D;
            t[i] = (int) (Fi % E);
            Fi_prev = Fi;
        }

        // Generate pattern
        long Fj_prev = A2, Fj;
        p[0] = A2 % E;
        for (int j = 1; j < M; j++) {
            Fj = ((Fj_prev+1)*B+C) % D;
            p[j] = (int) (Fj % E);
            Fj_prev = Fj;
        }
//        System.out.println("T: ");
//        print(t);
//        System.out.println("P: ");
//        print(p);

        // Generate levenshtein distance table
        int minLD=minLD(t,p);


//        System.out.println("minLD: "+minLD);
//        System.out.println("minLDRow: ");
//        print(minLDRow);

//        System.out.println("Words: ");
//
//        long[] w = new long[6];
//        w[0]=2;
//        w[1]=1;
//        w[2]=3;
//        w[3]=3;
//        w[4]=0;
//        w[5]=3;
//        System.out.println(LD(w,p));

        Node dictionary = new Node();
        for (int i = 0; i < minLDRow.length; i++) {
            if(minLDRow[i] != minLD) continue;
//            System.out.println("Index: "+i);
            for (int j = 1; j < p.length+minLD+1; j++) {
                if(i-j < 0) break;

                int[] w = Arrays.copyOfRange(t, i-j, i);
                if(LD(w, p) == minLD) {
                    //System.out.println("j: "+j);
                    //print(w);

                    // add to dictionary
                    dictionary.addWord(w, 0);
                }
            }
        }

        //print(dictionary, "");

        System.out.println((dictionary.cnt+1));
    }

    public static void print(long[] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.print(m[i] + "\t");
        }
        System.out.println("");
    }

    private static Integer min(int a, Integer b, int c) {
//        System.out.println("\t "+a+" "+b+" "+c);

        Integer ret = a;
        if(b != null && ret > b) {
            ret = b;
        }
        if(ret > c) {
            ret = c;
        }
        return ret;
    }

    private static void print(int[] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.print(m[i] + "\t");
        }
        System.out.println("");
    }

    private static void print(Integer[] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.print(m[i] + "\t");
        }
        System.out.println("");
    }

    private static int minLD(int[] t, int[] p) {
        Integer[] prevRow = new Integer[t.length+1];
        Integer[] currentRow = new Integer[t.length+1];
        Integer[] swap;
        int minLD = Integer.MAX_VALUE;
        for (int i = 0; i <= p.length; i++) {
            for (int k = 0; k <= t.length; k++) {
                if(k == 0) {
                    currentRow[0] = i;
                } else if(i == 0) {
                    currentRow[k] = 0;
                } else {
                    currentRow[k] = min(
                        prevRow[k] + 1,
                        (i < p.length) ? currentRow[k-1]+1 : null,
                        prevRow[k-1] + ((p[i-1]==t[k-1]) ? 0 : 1)
                    );

                    if(i == p.length && minLD > currentRow[k]) {
                        minLD = currentRow[k];
                    }
                }
            }

//            if(i!=0) {
//                System.out.println("Prev row:");
//                print(prevRow);
//            }
//            System.out.println("Current row:");
//            print(currentRow);

            swap = prevRow;
            prevRow = currentRow;
            currentRow = swap;

//            prevRow = Arrays.copyOf(currentRow, currentRow.length);
        }

        // prevRow because of swapping
        minLDRow = prevRow;
        // get minimum from the last cell
        return minLD;
    }

    private static int LD(int[] t, int[] p) {
        Integer[] prevRow = new Integer[t.length+1];
        Integer[] currentRow = new Integer[t.length+1];
        Integer[] swap;
        for (int i = 0; i <= p.length; i++) {
            for (int k = 0; k <= t.length; k++) {
                if(k == 0) {
                    currentRow[0] = i;
                } else if(i == 0) {
                    currentRow[k] = k;
                } else {
                    currentRow[k] = min(
                        prevRow[k] + 1,
                        (i < p.length) ? currentRow[k-1]+1 : null,
                        prevRow[k-1] + ((p[i-1]==t[k-1]) ? 0 : 1)
                    );
                }
            }

//            if(i!=0) {
//                System.out.println("Prev row:");
//                print(prevRow);
//            }
//            System.out.println("Current row:");
            //print(currentRow);

            swap = prevRow;
            prevRow = currentRow;
            currentRow = swap;

//            prevRow = Arrays.copyOf(currentRow, currentRow.length);
        }

        // get minimum from the last cell
        return prevRow[t.length];
    }

    private static void print(Node n, String depth) {
        for (Map.Entry<Integer, Node> entry : n.nodes.entrySet()) {
            System.out.println(depth+" "+entry.getKey());
            print(entry.getValue(), depth+"\t");
        }
    }
}
