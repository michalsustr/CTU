/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 *
 * @author michal
 */
public class Main {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));

        int N = sc.nextInt();
        double[][] laplacian = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                laplacian[i][j] = 0;
            }

        }

        while (true) {
            int from = sc.nextInt();
            int to = sc.nextInt();

            if (from == 0 && to == 0) {
                break;
            }

            laplacian[from][to] = -1;
            laplacian[to][from] = -1;
            laplacian[from][from]++;
            laplacian[to][to]++;
        }

        if(N == 1) {
            System.out.println("1");
        } else {
            System.out.println((int) determinant(laplacian));
        }
    }

    public static double determinant(double[][] A) {
        for (int i = 1; i < A.length - 1; i++) {
            for (int j = i + 1; j < A.length; j++) {
                for (int k = i + 1; k < A.length; k++) {
                    A[j][k] = A[j][k] * A[i][i] - A[j][i] * A[i][k];
                    if (i != 1) {
                        A[j][k] /= A[i - 1][i - 1];
                    }
                }
            }
        }

        return A[A.length - 1][A.length - 1];
    }
}
