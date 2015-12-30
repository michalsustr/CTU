/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author michal
 */
public class Main {

    private static int matrix[][];
    private static int inDegree[];
    private static int outDegree[];
    private static int N;
    private static StringBuilder sb;
    private static int edgeNum;
    private static String[] edges;
    private static int edgePtr;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // load input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        matrix = new int[N][N];
        inDegree = new int[N];
        outDegree = new int[N];
        for (int i = 0; i < N; i++) {
            inDegree[i] = 0;
            outDegree[i] = 0;
            for (int j = 0; j < N; j++) {
                matrix[i][j] = 0;
            }
        }

        // from forum
        edgeNum = 0;
        int node1, node2, k;
        String line;
        while (true) {
            line = br.readLine() + " ";      // add sentinel
            k = 0;
            node1 = 0;
            node2 = 0;

            while (line.charAt(k) != ' ') { // get first node
                node1 = node1 * 10 + line.charAt(k++) - 48;
            }

            while (line.charAt(++k) != ' ') {// get second node
                node2 = node2 * 10 + line.charAt(k) - 48;
            }

            if (node1 + node2 == 0) {
                break;
            }

            matrix[node1][node2]++;
            outDegree[node1]++;
            inDegree[node2]++;
            edgeNum++;
        }

        // check if there is eulerian path and choose starting vertex
        Integer startNode = null;
        int numberOfUnequalInAndOutDegrees = 0;
        Integer a = null, b = null;
        for (int i = 0; i < N; i++) {
            if (inDegree[i] != outDegree[i]) {
                numberOfUnequalInAndOutDegrees++;
                if (numberOfUnequalInAndOutDegrees > 2) {
                    System.out.println("-1");
                    return;
                }
                if (a == null) {
                    a = i;
                } else if (b == null) {
                    b = i;
                }
            }
        }
        if (numberOfUnequalInAndOutDegrees != 0 && numberOfUnequalInAndOutDegrees != 2) {
            System.out.println("-1");
            return;
        }
        if (numberOfUnequalInAndOutDegrees == 0) {
            startNode = 0;
        } else {
            if (outDegree[a] > inDegree[a] && inDegree[b] > outDegree[b]) {
                startNode = a;
            } else if (outDegree[b] > inDegree[b] && inDegree[a] > outDegree[a]) {
                startNode = b;
            } else {
                System.out.println("-1");
                return;
            }
        }

        edges = new String[edgeNum];
        edgePtr = 0;
        sb=new StringBuilder((edgeNum+1)*7);
        eulerPath(startNode);

        System.out.println(N);
        for(int i = edges.length-1; i >= 0; i--) {
            sb.append(edges[i]);
        }
        System.out.print(sb);
        System.out.println("0 0");
    }


    public static void eulerPath(int current) {
        for (int i = 0; i < N; i++) {
            if (matrix[current][i] > 0) {
                matrix[current][i]--;
                eulerPath(i);
                edges[edgePtr++] = current + " " + i + "\n";
            }
        }
    }
}