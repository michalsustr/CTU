package pal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    private static int C = 0, N = 0, H = 0, O = 0;
    private static int remainingBonds = 0;
    private static ArrayList<String> certificates;
    private static ArrayList<Node> nodes;
    private static int totalCnt = 0;

    public static void main(String[] args) throws IOException {
        readInput();
        nodes = new ArrayList<Node>();
        certificates = new ArrayList<String>();
        firstAtom();
        System.out.println("Certs: "+certCnt);
        System.out.println(totalCnt);
    }

    private static void readInput() throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(
                System.in));
        // BufferedReader inputStream = new BufferedReader(new
        // FileReader("example.in"));
        String line = inputStream.readLine().trim();

        char element = 0;
        int pos = 1;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == 'N' || c == 'O' || c == 'H' || c == 'C') {
                element = c;
                pos = 1;
            } else {
                switch (element) {
                    case 'N':
                        N = N * pos + c - '0';
                        break;
                    case 'C':
                        C = C * pos + c - '0';
                        break;
                    case 'H':
                        H = H * pos + c - '0';
                        break;
                    case 'O':
                        O = O * pos + c - '0';
                        break;
                }
                pos *= 10;
            }
        }
    }

    private static void firstAtom() {
        if (C > 0) {
            firstAtom(4);
        }
        if (N > 0) {
            firstAtom(3);
            firstAtom(5);
        }
        if (O > 0) {
            firstAtom(2);
        }
    }

    private static void firstAtom(int bonds) {
        addAtom(bonds);
        nextAtom();
        removeAtom();
    }

    private static void addAtom(int bonds) {
        nodes.add(new Node(bonds));
        switch (bonds) {
            case 2:
                O--;
                break;
            case 4:
                C--;
                break;
            case 3:
            case 5:
                N--;
                break;
        }
        remainingBonds += bonds;
    }

    private static void removeAtom() {
        Node lastNode = nodes.get(nodes.size()-1);
        switch (lastNode.bonds) {
            case 2:
                O++;
                break;
            case 4:
                C++;
                break;
            case 3:
            case 5:
                N++;
                break;
        }
        remainingBonds -= lastNode.remainingBonds;
        nodes.remove(lastNode);
    }
    private static int certCnt = 0;
    private static void nextAtom() {
        String certificate = getCertificate();
        certCnt++;
        System.out.println("Cert "+certificate);

        if (certificates.contains(certificate)) {
            return;
        }
        certificates.add(certificate);

        if (C == 0 && N == 0 && O == 0 && H == remainingBonds) {
//            System.out.println("Final: "+certificate);
            totalCnt++;
        }

        if (C > 0) {
            nextAtom(4);
        }
        if (N > 0) {
            nextAtom(3);
            nextAtom(5);
        }
        if (O > 0) {
            nextAtom(2);
        }
    }

    private static void nextAtom(int bonds) {
        addAtom(bonds);
        for (int i = 0; i < nodes.size() - 1; i++) { // skip the last added node
            int maxStrength = Math.min(
                    nodes.get(nodes.size() - 1).remainingBonds,
                    nodes.get(i).remainingBonds);
            for (int strength = 1; strength <= maxStrength; strength++) {
                if(investigateAtom(bonds, strength)) {
                    connectAtoms(nodes.size() - 1, i, strength);
                    nextAtom();
                    disconnectAtoms(nodes.size() - 1, i, strength);
                }
            }
        }

        removeAtom();
    }

    private static void connectAtoms(int a, int b, int strength) {
        Node A = nodes.get(a);
        Node B = nodes.get(b);
        A.connections.add(B);
        B.connections.add(A);
        A.remainingBonds -= strength;
        B.remainingBonds -= strength;
        remainingBonds -= 2 * strength;
    }

    private static void disconnectAtoms(int a, int b, int strength) {
        Node A = nodes.get(a);
        Node B = nodes.get(b);
        assert A.connections.get(A.connections.size() - 1) == B;
        assert B.connections.get(B.connections.size() - 1) == A;
        A.connections.remove(B);
        B.connections.remove(A);
        A.remainingBonds += strength;
        B.remainingBonds += strength;
        remainingBonds += 2 * strength;
    }

    private static void print(ArrayList<Node> nodes) {
        for(Node n: nodes) {
            System.out.print(n.bonds);
        }
        System.out.println("");
    }

    private static String getCertificate() {
        return new TreeCertificate(nodes).getCertificate();
    }

    private static boolean investigateAtom(int bonds, int strength) {
        return true;
    }

    private static class Node {

        int bonds;
        int remainingBonds;
        ArrayList<Node> connections;

        public Node(int bonds) {
            this.bonds = bonds;
            this.remainingBonds = bonds;
            this.connections = new ArrayList<Node>();
        }
    }

    private static class TreeCertificate {

        private ArrayList<Node> nodes;
        private boolean removed[];
        private boolean leaves[];
        private ArrayList<String> certificates;

        public TreeCertificate(ArrayList<Node> nodes) {
            this.nodes = nodes;
            removed = new boolean[this.nodes.size()];
            leaves = new boolean[this.nodes.size()];

            for (int i = 0; i < removed.length; i++) {
                removed[i] = false;
                leaves[i] = false;
            }

            certificates = new ArrayList<String>();
        }

        String getCertificate() {
            initCertificates();
            int n = nodes.size();
            while (n > 2) {
                fillLeaves();
                for (int i = 0; i < nodes.size(); ++i) {
                    if (removed[i] || leaves[i]) {
                        continue;
                    }
                    Node node = nodes.get(i);
                    ArrayList<String> subcerts = new ArrayList<String>();
                    fillSubcerts(node, subcerts);
                    if (subcerts.size() > 0) {
                        n -= subcerts.size();
                        subcerts.add(stripped(certificates.get(i)));
                        Collections.sort(subcerts);
                        certificates.set(i, "(" + join(subcerts) + ")");
                    }
                }
            }

            String certificate = "";
            for (int i = 0; i < nodes.size(); ++i) {
                if (!removed[i]) {
                    if (certificate.compareTo(certificates.get(i)) > 0) {
                        certificate = certificate + certificates.get(i);
                    } else {
                        certificate = certificates.get(i) + certificate;
                    }
                }
            }
            return certificate;
        }

        private void initCertificates() {
            for (int i = 0; i < nodes.size(); ++i) {
                certificates.add("(" + nodes.get(i).bonds + nodes.get(i).remainingBonds + ")");
            }
        }

        private void fillLeaves() {
            for (int i = 0; i < nodes.size(); ++i) {
                if (!removed[i]) {
                    Node node = nodes.get(i);
                    int conn_count = 0;
                    for (Node conn : node.connections) {
                        int idx = nodes.indexOf(conn);
                        if (!removed[idx]) {
                            conn_count++;
                        }
                    }
                    leaves[i] = (conn_count == 1);
                }
            }
        }

        private void fillSubcerts(Node node, ArrayList<String> subcerts) {
            for (Node conn : node.connections) {
                int idx= nodes.indexOf(conn);
                if (removed[idx] || !leaves[idx]) {
                    continue;
                }
                subcerts.add(certificates.get(idx));
                removed[idx] = true;
            }
        }

        private String stripped(String s) { // strip parenthesis
            return s.substring(1, s.length() - 1);
        }

        private String join(ArrayList<String> subcerts) {
            StringBuilder out = new StringBuilder();
            for (String s : subcerts) {
              out.append(s.toString());
            }
            return out.toString();
        }
    }
}
