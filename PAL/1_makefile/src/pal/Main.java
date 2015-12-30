package pal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author michal
 */
public class Main {
    private static HashMap<String, Integer> targets;
    private static ArrayList<String> lines;

    private static HashSet<String> expanding;
    private static HashSet<String> closed;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        // list of all input lines from stdin
        lines = new ArrayList<String>(10000);
        targets = new HashMap<String, Integer>(20000);
        String firstNode = null;

        while(true) {
            line = br.readLine();
            if(line == null) {
                break;
            }
            lines.add(line);

            if(line.charAt(0) != '\t') { // target processing
                // parse target: subtarget1 subtarget2 ...
                String target = line.substring(0, line.indexOf(":"));
                targets.put(target, lines.size()-1);
                if(firstNode == null) {
                    firstNode = target;
                }
            }
        }

        //if(2>1) return;

        // process the rels table, DFS
        expanding = new HashSet<String>(1000);
        closed = new HashSet<String>(1000);

        // start open list with the first node in input (should be target all:)
        if(dfs(firstNode)) {
            System.out.println("ERROR");
        } else {
            StringBuilder sb = new StringBuilder();
            String target = null;
            for (Iterator<String> it = lines.iterator(); it.hasNext();) {
                line = it.next();
                // get target for this line
                if(line.charAt(0) != '\t') {
                    // parse target: subtarget1 subtarget2 ...
                    String[] tokens = line.split(":");
                    target = tokens[0];
                }

                if(closed.contains(target)) {
                    sb.append(line+"\n");
                } else {
                    sb.append("#"+line+"\n");
                }
                // flush sb
                if(sb.length() > 1e7) {
                    System.out.print(sb);
                    sb.setLength(0);
                }
            }
            System.out.print(sb);
        }
    }

    /**
     * @return true for error, false for no error
     */
    private static boolean dfs(String current) {
        //System.out.println(current);
        Integer targetLine = targets.get(current);

        // this node does not have a definition, it was only referenced
        // therefore it is an error
        if(targetLine == null) {
            return true;
        }
        String targetDef = lines.get(targetLine);
        String[] subtargets = targetDef.substring(targetDef.indexOf(":")+1).split(" ");
        // expand current node
        expanding.add(current);
        // loop child nodes
        for(int i=0; i<subtargets.length; i++) {
            String next = subtargets[i];
            if(next.isEmpty()) {
                continue;
            }

            if(closed.contains(next)) {
                continue;
            }
            if(expanding.contains(next)) {
                return true;
            }

            if(dfs(next)) {
                return true;
            }
        }
        expanding.remove(current);
        closed.add(current);
        return false;
    }
}
