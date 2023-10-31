import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AdkMaxFlow {

    Kattio io;

    boolean bfs(int nodes, ArrayList<Pair> edges, int s, int t, int[] path) {

        boolean visited[] = new boolean[nodes];
        for (int i = 0; i < nodes; i++) {
            visited[i] = false;
        }

        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v = 0; v < nodes; v++) {
                if (edges.contains(new Pair(u, v))) {
                    if (!visited[v] && edges.get(u).getResidualCapacity() > 0) {

                        if (v == t) {
                            path[v] = u;
                            return true;
                        }

                        q.add(v);
                        path[v] = u;
                        visited[v] = true;
                    }
                }
            }
        }

        return false;

    }

    void solveFlow() {
        int nodes = 4;
        int s = 1;
        int t = 4;
        int e = 5;

        Map<Pair, Pair> edges = new HashMap<Pair, Pair>();

        /*
         * 
         * 
         * for (int i = 0; i < e; ++i) {
         * // Flöde f från a till b
         * int a = io.getInt();
         * int b = io.getInt();
         * int f = io.getInt();
         * 
         * edges.put(new Pair(a, b), new Pair(a, b, f));
         * }
         */

        edges.put(new Pair(1, 2), new Pair(1, 2, 1));
        edges.put(new Pair(1, 3), new Pair(1, 3, 2));
        edges.put(new Pair(2, 4), new Pair(2, 4, 2));
        edges.put(new Pair(3, 2), new Pair(3, 2, 2));
        edges.put(new Pair(3, 4), new Pair(3, 4, 1));

        // implement ford-fulkerson here
        for (Pair edge : edges.values()) {
            // flow u -> v = 0, flow v -> u = 0
            // rest capacity u -> v = capacity u -> v, rest capacity v -> u = capacity v ->
            // u
            // while
            int a = edge.getFirst();
            int b = edge.getSecond();
            edge.setFlow(0);

            Pair reverseEdge = new Pair(b, a);
            reverseEdge.setFlow(0);

        }

        int[] path = new int[nodes];

        while (bfs(nodes, new ArrayList<>(edges.values()), s, t, path)) {
            int currentFlow = Integer.MAX_VALUE;
            for (int v = t; v != s; v = path[v]) {
                int u = path[v];
                Pair edge = edges.get(new Pair(u, v));
                currentFlow = Math.min(currentFlow, edge.getResidualCapacity());
            }

            for (int v = t; v != s; v = path[v]) {
                int u = path[v];
                Pair edge = edges.get(new Pair(u, v));
                Pair reverseEdge = edges.get(new Pair(v, u));

                edge.setFlow(edge.getFlow() + currentFlow);
                reverseEdge.setFlow(-edge.getFlow());
            }
        }

        System.out.println("Number of nodes: " + nodes);
        System.out.println("Source node: " + s);
        System.out.println("Target node: " + t);

        int maxFlow = 0;
        for (Pair edge : edges.values()) {
            if (edge.getFirst() == s) { // Summing the flow from source node
                maxFlow += edge.getFlow();
            }
        }
        System.out.println("Max flow: " + maxFlow);

        System.out.println("Edge pairs and their corresponding final flow:");
        for (Pair edge : edges.values()) {
            System.out.println("(" + edge.getFirst() + ", " + edge.getSecond() + "): " + edge.getFlow());
        }

    }

    AdkMaxFlow() {
        io = new Kattio(System.in, System.out);

        solveFlow();

        io.close();
    }

    public static void main(String args[]) {
        System.out.println("Hello World!");
        new AdkMaxFlow();
    }

}
