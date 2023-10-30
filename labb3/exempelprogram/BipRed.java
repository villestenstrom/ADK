/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {
	Kattio io;
	int x, y, e, v, maxMatch = 0;
	Pair[] edges;

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
		io.println(v);
		io.println(s + " " + t);
		io.println(new_e);
		for (int i = 0; i < e; ++i) {
			int a = edges[i].getFirst(), b = edges[i].getSecond();
			// Kant från a till b med kapacitet c
			io.println(a + " " + b + " " + c);
		}

		// Create edges from source to x and from y to sink
		for (int i = 1; i <= x; i++) {
			io.println(s + " " + i + " " + c);
		}

		for (int i = x + 1; i <= x + y; i++) {
			io.println(i + " " + t + " " + c);
		}


		// Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
		io.flush();

		// Debugutskrift
		System.err.println("Skickade iväg flödesgrafen");
	}

	void solveFlow() {
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int e = io.getInt();
		edges = new Pair[e];

		for (int i = 0; i < e; ++i) {
			// Flöde f från a till b
			int a = io.getInt();
			int b = io.getInt();
			int f = io.getInt();
			
			edges[i] = new Pair(a, b, f);
		}

		// implement ford-fulkerson here
		for (Pair edge : edges) {
			// flow u -> v = 0, flow v -> u = 0
			// rest capacity u -> v = capacity u -> v, rest capacity v -> u = capacity v -> u
			// while 
			
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

	BipRed() {
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

		readMaxFlowSolution();

		writeBipMatchSolution();

		// debugutskrift
		System.err.println("Bipred avslutar\n");

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
	}

	public static void main(String args[]) {
		new BipRed();
	}
}
