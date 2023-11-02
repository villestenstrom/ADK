import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
* @author: Ville Stenström, Melvin Amandusson
*/

public class AdkMaxFlow {

    Kattio io;
    List<List<Pair>> adjList;

    boolean bfs(int nodes, int s, int t, int[] parent) {
        boolean visited[] = new boolean[nodes + 1];
        for (int i = 1; i <= nodes; i++) {
            visited[i] = false;
        }

        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (Pair currentEdge : adjList.get(u)) {
                int v = currentEdge.getSecond();
                if (!visited[v] && currentEdge.getResidualCapacity() > 0) {
                    parent[v] = u;
                    if (v == t)
                        return true;
                    q.add(v);
                    visited[v] = true;
                }
            }
        }

        return false;
    }

    void solveFlow(boolean debug) {
        int nodes, s, t, e;
        Map<Pair, Pair> edges = new LinkedHashMap<Pair, Pair>();

        if (debug) {

            nodes = 4;
            s = 1;
            t = 4;
            e = 5;

            edges.put(new Pair(1, 2), new Pair(1, 2, 1));
            edges.put(new Pair(1, 3), new Pair(1, 3, 2));
            edges.put(new Pair(2, 4), new Pair(2, 4, 2));
            edges.put(new Pair(3, 2), new Pair(3, 2, 2));
            edges.put(new Pair(2, 3), new Pair(2, 3, 1));
            edges.put(new Pair(3, 4), new Pair(3, 4, 1));
        } else {
            nodes = io.getInt();
            s = io.getInt();
            t = io.getInt();
            e = io.getInt();

            adjList = new ArrayList<>(nodes + 1);
            for (int i = 0; i <= nodes; i++) {
                adjList.add(new ArrayList<>());
            }

            for (int i = 0; i < e; ++i) {
                int a = io.getInt();
                int b = io.getInt();
                int f = io.getInt();

                Pair edge = new Pair(a, b, f);
                edges.put(new Pair(a, b), edge);
                adjList.get(a).add(edge);

                // Lägg till omvänd kant om den inte redan finns
                if (!edges.containsKey(new Pair(b, a))) {
                    Pair reverseEdge = new Pair(b, a, 0); // Kapacitet 0 till en början
                    edges.put(new Pair(b, a), reverseEdge);
                    adjList.get(b).add(reverseEdge);
                }

            }
        }

        // implement ford-fulkerson here
        for (Pair edge : edges.values()) {
            // flow u -> v = 0, flow v -> u = 0
            // rest capacity u -> v = capacity u -> v, rest capacity v -> u = capacity v ->
            // u
            // while
            int a = edge.getFirst();
            int b = edge.getSecond();
            edge.setFlow(0);

        }

        int[] parent = new int[10000000];

        while (bfs(nodes, s, t, parent)) {

            int currentMaxFlow = Integer.MAX_VALUE;
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                Pair edge = edges.get(new Pair(u, v));
                currentMaxFlow = Math.min(currentMaxFlow, edge.getResidualCapacity());
            }

            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                Pair edge = edges.get(new Pair(u, v));

                edge.setFlow(edge.getFlow() + currentMaxFlow);

                // Uppdatera det residuala flödet för den omvända kanten
                Pair reverseEdge = edges.get(new Pair(v, u));
                reverseEdge.setFlow(reverseEdge.getFlow() - currentMaxFlow);

            }
        }

        int maxFlow = 0;
        for (Pair edge : edges.values()) {
            if (edge.getFirst() == s) {
                maxFlow += edge.getFlow();
            }
        }

        ArrayList<Pair> edgesWithFlow = new ArrayList<>();
        for (Pair edge : edges.values()) {
            if (edge.getFlow() > 0 && edge.getFlow() <= edge.getCapacity()) {
                edgesWithFlow.add(edge);
            }
        }

        io.println(nodes);
        io.println(s + " " + t + " " + maxFlow);
        io.println(edgesWithFlow.size());

        for (Pair edge : edgesWithFlow) {
            io.println(edge.getFirst() + " " + edge.getSecond() + " " + edge.getFlow());
        }

        if (debug) {
            System.out.println(nodes);
            System.out.println(s + " " + t + " " + maxFlow);
            System.out.println(edgesWithFlow.size());
            for (Pair edge : edgesWithFlow) {
                System.out.println(edge.getFirst() + " " + edge.getSecond() + " " + edge.getFlow());
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
        new AdkMaxFlow();
    }
}
