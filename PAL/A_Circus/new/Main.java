package pal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class Main {

    private static int N, M;
    private static Node[] points;
    static int maxProfit = 0;

    public static void main(String[] args) throws IOException {
        loadInput();
        findRegions();
        initRegions();
        loopRegions();

        if(maxProfit > 0) {
            System.out.println(maxProfit);
        } else {
            System.out.println("0");
        }
    }

    private static void loadInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // from forum
        N = 0;
        M = 0;
        int price = 0, profit = 0, k;
        String line;
        k = 0;
        line = br.readLine() + " ";      // add sentinel
        while (line.charAt(k) != ' ') { // get N
            N = N * 10 + line.charAt(k++) - 48;
        }
        while (line.charAt(++k) != ' ') {// get M
            M = M * 10 + line.charAt(k) - 48;
        }

        points = new Node[N];
        for (int i = 0; i < N; i++) {
            k = 0;
            price = 0;
            profit = 0;
            line = br.readLine() + " ";      // add sentinel
            while (line.charAt(k) != ' ') { // get price
                price = price * 10 + line.charAt(k++) - 48;
            }
            if (k != line.length() - 1) {
                while (line.charAt(++k) != ' ') { // get profit
                    profit = profit * 10 + line.charAt(k) - 48;
                }
            }
            points[i] = new Node(i, price, profit);
        }

        int from, to;
        for (int i = 0; i < M; i++) {
            k = 0;
            from = 0;
            to = 0;
            line = br.readLine() + " ";      // add sentinel
            while (line.charAt(k) != ' ') { // get from
                from = from * 10 + line.charAt(k++) - 48;
            }
            while (line.charAt(++k) != ' ') { // get to
                to = to * 10 + line.charAt(k) - 48;
            }
            points[from - 1].addNeighbour(points[to - 1]);
        }
    }

    // TARJAN
    private static List<Region> regions;
    private static Stack tarjanStack;
    private static int tIndex = 0;
    private static int topoIndex = 0;
    private static void findRegions() {
        tarjanStack = new Stack();
        regions = new ArrayList<Region>(); // list of strongly connected components
        for (int i = 0; i < N; i++) {
            if (points[i].label == -1) {
                tarjanAlgorithm(points[i]);
            }
        }
    }
    private static void tarjanAlgorithm(Node node) {
        node.label = tIndex;
        node.lowlink = tIndex;
        tIndex++;

        tarjanStack.push(node); //pridej na zasobnik
        for (Node neighbour : node.getNeighbours()) {
            if (neighbour.label == -1) {
                tarjanAlgorithm(neighbour);
                node.lowlink = node.lowlink < neighbour.lowlink ? node.lowlink : neighbour.lowlink;
            } else if (tarjanStack.contains(neighbour)) {
                node.lowlink = node.lowlink < neighbour.lowlink ? node.lowlink : neighbour.lowlink;
            }

        }

        if (node.lowlink == node.label) { //pokud jsme v koreni komponenty
            Node n = null;
            Region component = new Region(topoIndex++); //seznam uzlu dane komponenty
            do {
                n = (Node) tarjanStack.pop(); //vyber uzel ze zasobniku
                component.add(n); //pridej ho do komponenty
            } while (n.index != node.index); //dokud nejsme v koreni
            regions.add(component); //komponentu pridej do seznamu komponent
        }
    }

    // LOOPING REGIONS
    private static void initRegions() {
        for(Region r : regions) {
            r.init();
        }
    }
    private static void loopRegions() {
        ListIterator li = regions.listIterator(regions.size());
        int i = 1;
        // Iterate in reverse.
        while (li.hasPrevious()) {
            Region r  = (Region) li.previous();
            r.searchRegion();
        }
    }
}
