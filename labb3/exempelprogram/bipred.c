/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder scanf/printf i C.
 *
 * Author: Per Austrin
 */

#include <stdio.h>


void readBipartiteGraph() {
  int x, y, e, i;
  // Läs antal hörn och kanter
  scanf("%d%d%d", &x, &y, &e);

  // Läs in kanterna
  for (int i = 0; i < e; ++i) {
    int a, b;
    scanf("%d %d", &a, &b);
  }
}


void writeFlowGraph() {
  int v = 23, e = 0, s = 1, t = 2;

  // Skriv ut antal hörn och kanter samt källa och sänka
  printf("%d\n%d %d\n%d\n", v, s, t, e);
  for (int i = 0; i < e; ++i) {
    int u, v, c;
    // Kant från u till v med kapacitet c
    printf("%d %d %d\n", u, v, c);
  }
  // Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
  fflush(stdout);

  // Debugutskrift
  fprintf(stderr, "Skickade iväg flödesgrafen\n");
}


void readMaxFlowSolution() {
  int v, e, s, t, f;

  // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
  // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
  // skickade iväg)
  scanf("%d%d%d%d%d", &v, &s, &t, &f, &e);

  for (int i = 0; i < e; ++i) {
    int u, v, f;
    // Flöde f från u till v
    scanf("%d%d%d", &u, &v, &f);
  }
}


void writeBipMatchSolution() {
  int x = 17, y = 4711, maxMatch = 0;

  // Skriv ut antal hörn och storleken på matchningen
  printf("%d %d\n%d\n", x, y, maxMatch);

  for (int i = 0; i < maxMatch; ++i) {
    int a, b;
    // Kant mellan a och b ingår i vår matchningslösning
    printf("%d %d\n", a, b);
  }

}


int main(void) {
  readBipartiteGraph();

  writeFlowGraph();

  readMaxFlowSolution();

  writeBipMatchSolution();

  // debugutskrift
  fprintf(stderr, "Bipred avslutar\n");
  return 0;
}
