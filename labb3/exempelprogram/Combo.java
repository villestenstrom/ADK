import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin, Ville Stenström, Melvin Amandusson
 */

public class Combo {
	Kattio io;
	int x, y, e, v, maxMatch = 0;
	Pair[] edges;
	List<List<Pair>> adjList;

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

	List<Pair> writeFlowGraph() {
		v = x + y + 2;
		int s = x + y + 1, t = s + 1, c = 10;

		// Skapa en lista för att lagra kanterna
		List<Pair> flowEdgesList = new ArrayList<>();

		for (int i = 0; i < e; ++i) {
			int a = edges[i].getFirst(), b = edges[i].getSecond();
			flowEdgesList.add(new Pair(a, b, c)); // Lägg till kant med kapacitet
		}

		for (int i = 1; i <= x; i++) {
			flowEdgesList.add(new Pair(s, i, c)); // Lägg till kanter från källa till x
		}

		for (int i = x + 1; i <= x + y; i++) {
			flowEdgesList.add(new Pair(i, t, c)); // Lägg till kanter från y till sänka
		}

		// Anropa solveFlow med den genererade listan
		return solveFlow(v, s, t, flowEdgesList);
	}

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

	List<Pair> solveFlow(int nodes, int s, int t, List<Pair> edgesList) {
		Map<Pair, Pair> edges = new LinkedHashMap<Pair, Pair>();

		adjList = new ArrayList<>(nodes + 1);
		for (int i = 0; i <= nodes; i++) {
			adjList.add(new ArrayList<>());
		}

		for (Pair edge : edgesList) {
			int a = edge.getFirst();
			int b = edge.getSecond();
			int f = edge.getCapacity();

			edges.put(new Pair(a, b), edge);
			adjList.get(a).add(edge);

			// Lägg till omvänd kant om den inte redan finns
			if (!edges.containsKey(new Pair(b, a))) {
				Pair reverseEdge = new Pair(b, a, 0); // Kapacitet 0 till en början
				edges.put(new Pair(b, a), reverseEdge);
				adjList.get(b).add(reverseEdge);
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

		return edgesWithFlow;
	}

	void readMaxFlowSolution(List<Pair> resultEdgesWithFlow) {
		maxMatch = 0;
		for (Pair edge : resultEdgesWithFlow) {
			int a = edge.getFirst();
			int b = edge.getSecond();
			if (a != x + y + 1 && a != x + y + 2 && b != x + y + 1 && b != x + y + 2) {
				maxMatch++;
			}
		}
		edges = resultEdgesWithFlow.toArray(new Pair[0]);
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

	Combo() {
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		List<Pair> resultEdgesWithFlow = writeFlowGraph();

		readMaxFlowSolution(resultEdgesWithFlow);

		writeBipMatchSolution();

		// debugutskrift
		System.err.println("Bipred avslutar\n");

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
	}

	public static void main(String args[]) {
		new Combo();
	}
}
