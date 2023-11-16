import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdkBipMatch {

    Kattio io;
    List<List<Pair>> adjList;
	int x, y, e, v, maxMatch = 0;
	Pair[] edges;
    int[] solveFlowData;

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
            if (edge.getFlow() > 0) {
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

	void readBipartiteGraph() {
		// Läs antal hörn och kanter
		x = io.getInt();
		y = io.getInt();
		e = io.getInt();
		edges = new Pair[e];

		// Läs in kanterna
		for (int i = 0; i < e; ++i) {
			int a = io.getInt();
			int b = io.getInt();
			// Kant från a till b
			edges[i] = new Pair(a, b);

		}
	}

	void writeFlowGraph() {
		v = x + y + 2;
		int s = x + y + 1, t = s + 1, c = 10;
		int new_e = e + x + y;

		// Skriv ut antal hörn och kanter samt källa och sänka
		solveFlowData[0] = v;  
        solveFlowData[1] = s;
        solveFlowData[2] = t;
        solveFlowData[3] = new_e;
		for (int i = 0; i < e; ++i) {
			int a = edges[i].getFirst(), b = edges[i].getSecond();
			// Kant från a till b med kapacitet c
			solveFlowData[4 + i * 3] = a;
            solveFlowData[4 + i * 3 + 1] = b;
            solveFlowData[4 + i * 3 + 2] = c;
		}

		// Create edges from source to x and from y to sink
		for (int i = 1; i <= x; i++) {
			io.println(s + " " + i + " " + c);
		}

		for (int i = x + 1; i <= x + y; i++) {
			io.println(i + " " + t + " " + c);
		}
	}

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
		int nodes = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int e = io.getInt();

		Map<Pair, Pair> edges = new HashMap<Pair, Pair>();

		for (int i = 0; i < e; ++i) {
			// Flöde f från a till b
			int a = io.getInt();
			int b = io.getInt();
			int f = io.getInt();

			edges.put(new Pair(a, b), new Pair(a, b, f));
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

			Pair reverseEdge = new Pair(b, a);
			reverseEdge.setFlow(0);

		}

		int[] path = new int[v];

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

	}

	void readMaxFlowSolution() {
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
		// skickade iväg)
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int totflow = io.getInt();
		int e = io.getInt();
		edges = new Pair[e];

		for (int i = 0; i < e; ++i) {
			// Flöde f från a till b
			int a = io.getInt();
			int b = io.getInt();
			int f = io.getInt();

			if (a != s && a != t && b != s && b != t) {
				maxMatch++;
				edges[i] = new Pair(a, b);
			}

		}
	}

	void writeBipMatchSolution() {

		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(maxMatch);

		for (int i = 0; i < maxMatch; ++i) {

			// Kant mellan a och b ingår i vår matchningslösning
			io.println(edges[i].getFirst() + " " + edges[i].getSecond());
		}

	}

	AdkBipMatch() {
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

        solveFlow();

		readMaxFlowSolution();

		writeBipMatchSolution();

		// debugutskrift
		System.err.println("Bipred avslutar\n");

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
	}

	public static void main(String args[]) {
		new AdkBipMatch();
	}
}