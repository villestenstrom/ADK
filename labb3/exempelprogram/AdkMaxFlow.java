import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdkMaxFlow {

    Kattio io;

    boolean bfs(int nodes, ArrayList<Pair> edges, int s, int t, int[] parent) {

        boolean visited[] = new boolean[nodes + 1];
        for (int i = 1; i <= nodes; i++) {
            visited[i] = false;
        }

        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int i = 0; i < edges.size(); i++) {

                Pair currentEdge = edges.get(i);
                int v = currentEdge.getSecond();

                if (currentEdge.getFirst() == u) {
                    if (!visited[v] && currentEdge.getResidualCapacity() > 0) {

                        parent[v] = u;

                        if (currentEdge.getSecond() == t) {
                            return true;
                        }

                        q.add(v);
                        visited[v] = true;

                    }
                }
            }
        }

        return false;

    }

    void solveFlow(boolean debug) {
        int nodes = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int e = io.getInt();

        Map<Pair, Pair> edges = new HashMap<Pair, Pair>();

        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();

            edges.put(new Pair(a, b), new Pair(a, b, f));
        }

        if (debug) {
            edges.clear();

            nodes = 4;
            s = 1;
            t = 4;
            e = 5;

            edges.put(new Pair(1, 2), new Pair(1, 2, 1));
            edges.put(new Pair(1, 3), new Pair(1, 3, 2));
            edges.put(new Pair(2, 4), new Pair(2, 4, 2));
            edges.put(new Pair(3, 2), new Pair(3, 2, 2));
            edges.put(new Pair(3, 4), new Pair(3, 4, 1));
        }

        Map<Pair, Pair> reverseEdges = new HashMap<Pair, Pair>();

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
            reverseEdges.put(new Pair(b, a), reverseEdge);

        }

        int[] parent = new int[nodes + 1];

        while (bfs(nodes, new ArrayList<>(edges.values()), s, t, parent)) {

            int currentMaxFlow = Integer.MAX_VALUE;
            for (int v = t; v != s; v = parent[v]) {

                int u = parent[v];

                System.out.println(" v: " + v + " u: " + u);

                Pair edge = edges.get(new Pair(u, v));
                currentMaxFlow = Math.min(currentMaxFlow, edge.getResidualCapacity());
            }

            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                Pair edge = edges.get(new Pair(u, v));
                // Pair reverseEdge = reverseEdges.get(new Pair(v, u));

                edge.setFlow(edge.getFlow() + currentMaxFlow);
                // reverseEdge.setFlow(-edge.getFlow());
            }
        }

        if (debug) {
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
        } else {
            int maxFlow = 0;
            for (Pair edge : edges.values()) {
                if (edge.getFirst() == s) { // Summing the flow from source node
                    maxFlow += edge.getFlow();
                }
            }

            io.println(nodes);
            io.println(s + " " + t + " " + maxFlow);
            io.println(e);

            for (Pair edge : edges.values()) {
                io.println(edge.getFirst() + " " + edge.getSecond() + " " + edge.getFlow());
            }
        }

    }

    AdkMaxFlow() {
        io = new Kattio(System.in, System.out);

        boolean debug = false;

        solveFlow(debug);

        io.close();
    }

    public static void main(String args[]) {
        System.out.println("Hello World!");
        new AdkMaxFlow();
    }

}
