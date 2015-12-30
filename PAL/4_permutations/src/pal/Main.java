/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));

        int N = sc.nextInt();
        int M = sc.nextInt();
        // starting permutation
        int[] sp = new int[M];
        for(int i = 0; i < M; i++) {
            sp[i] = sc.nextInt();
        }
        // last permutation
        int[] lp = new int[M];
        for(int i = 0; i < M; i++) {
            lp[i] = sc.nextInt();
        }

        int[] ep = new int[N];
        int maxBH = Integer.MIN_VALUE;
        int[] resultP = new int[M];
        boolean isStart = true;
        while(true) {
            if(isStart) {
                isStart = false;
            } else {
                if(!next_permutation(sp)) {
                    break;
                }
            }

            // calculate extended permutation
            for (int i = 0; i < N/M; i++) {
                for(int j = 0; j < M; j++) {
                    ep[(j+i*M)] = sp[j]+i*M;
                }
            }
//            print_perm(sp);
//            print_perm(lp);
            int max = 0, min = 0, diffBH = 0, Nremaining = N, groupSize = 0;
            for (int i = 0; i < N; i++) {
                if(groupSize == 0) {
                    groupSize = getFirstBit(Nremaining);
                    //System.out.println("Group "+groupSize+" max: "+max+" min: "+min);

                    diffBH += max - min;
                    max=Integer.MIN_VALUE;
                    min=Integer.MAX_VALUE;
                    Nremaining -= groupSize;
                }

                if(ep[i] > max) {
                    max = ep[i];
                }
                if(ep[i] < min) {
                    min = ep[i];
                }
                groupSize--;
            }
            //System.out.println(" max: "+max+" min: "+min);
            diffBH += max - min;
            //System.out.println("DIFF "+diffBH);

            if(maxBH < diffBH) {
                maxBH = diffBH;
                resultP = Arrays.copyOf(sp, sp.length);
            }

            boolean doBreak = true;
            for (int i = 0; i < lp.length; i++) {
                if(lp[i] != sp[i]) doBreak = false;
            }
            if(doBreak) {
                break;
            }
        }

        System.out.println(maxBH);
        print_perm(resultP);

    }

    // C++ next_permutation() analog in Java
    static boolean next_permutation(int[] p) {
        for (int a = p.length - 2; a >= 0; --a) {
            if (p[a] < p[a + 1]) {
                for (int b = p.length - 1;; --b) {
                    if (p[b] > p[a]) {
                        int t = p[a];
                        p[a] = p[b];
                        p[b] = t;
                        for (++a, b = p.length - 1; a < b; ++a, --b) {
                            t = p[a];
                            p[a] = p[b];
                            p[b] = t;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void print_perm(int[] sp) {
        for (int i = 0; i < sp.length; i++) {
            System.out.print(sp[i]);
            if(i != sp.length-1) {
                System.out.print(" ");
            }
        }
        System.out.println("");
    }

    private static int getFirstBit(int N) {
        for (int i = 24; i >= 0; i--) { // 24 bit checking because N <= 2000000 which has 23rd bit set
            if((N & (1 << i)) == (1 << i)) {
                return (1 << i);
            }
        }
        return -1;
    }
}
