/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder iostreams i C++.
 * Anmärkning: scanf/printf-rutinerna från C-exemplet går att använda
 * även i C++ och är ofta märkbart snabbare än cin/cout som det här
 * programmet använder.
 *
 * Author: Per Austrin
 */

#include <iostream>

using std::cin;
using std::cout;
using std::cerr;

void readBipartiteGraph() {
  int x, y, e;
  // Läs antal hörn och kanter
  cin >> x >> y >> e;

  // Läs in kanterna
  for (int i = 0; i < e; ++i) {
    int a, b;
    cin >> a >> b;
  }
}


void writeFlowGraph() {
  int v = 23, e = 0, s = 1, t = 2;

  // Skriv ut antal hörn och kanter samt källa och sänka
  cout << v << "\n" << s << " " << t << "\n" << e << "\n"; 
  for (int i = 0; i < e; ++i) {
    int u, v, c;
    // Kant från u till v med kapacitet c
    cout << u << " " << v << " " << c << "\n";
  }
  // Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
  cout.flush();

  // Debugutskrift
  cerr << "Skickade iväg flödesgrafen\n";
}


void readMaxFlowSolution() {
  int v, e, s, t, f;

  // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
  // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
  // skickade iväg)
  cin >> v >> s >> t >> f >> e;

  for (int i = 0; i < e; ++i) {
    int u, v, f;
    // Flöde f från u till v
    cin >> u >> v >> f;
  }
}


void writeBipMatchSolution() {
  int x = 17, y = 4711, maxMatch = 0;

  // Skriv ut antal hörn och storleken på matchningen
  cout << x << " " << y << "\n" << maxMatch << "\n";

  for (int i = 0; i < maxMatch; ++i) {
    int a, b;
    // Kant mellan a och b ingår i vår matchningslösning
    cout << a << " " << b << "\n";
  }

}


int main(void) {
  // Två trick för att göra cin/cout lite snabbare.
  // Se http://kattis.csc.kth.se/doc/iostreamio
  std::ios::sync_with_stdio(false);
  cin.tie(0);

  readBipartiteGraph();

  writeFlowGraph();

  readMaxFlowSolution();

  writeBipMatchSolution();

  // debugutskrift
  cerr << "Bipred avslutar\n";
  return 0;
}
