1 Jämför tidskomplexiteten för Edmonds-Karps algoritm då grafen implementeras som en grannmatris och då den implementeras med grannlistor. (För att satsen f[v,u]:= -f[u,v] ska kunna implementeras effektivt måste grannlisteimplementationen utökas så att varje kant har en pekare till den omvända kanten.)

2 Uttryck tidskomplexiteten i n och m där n är totala antalet hörn och m antalet kanter i den bipartita grafen. Välj sedan den implementation som är snabbast då m=O(n), alltså då grafen är gles.
Kalle menar att om vi börjar med en bipartit graf G och gör om den till en flödesgraf H med ny källa s och nytt utlopp t så kommer avståndet från s till t att vara 3.

Kalle tycker därför att BFS-steget alltid kommer att hitta en stig av längd 3 i restflödesgrafen (om det finns någon stig).

3. Det första påståendet är sant, men inte det andra. Varför har stigarna som BFS hittar i restflödesgrafen inte nödvändigtvis längd 3? Hur långa kan de bli?
Anledningen till att bipartit matchning kan reduceras till flöde är att en lösning till flödesproblemet kan tolkas som en lösning till matchningsproblemet. Detta gäller bara om det flöde som algoritmen ger är ett heltalsflöde (flödet i varje kant är ett heltal), vilket i detta fall innebär att flödet längs en kant antingen är 0 eller 1. Som tur är så är det på det sättet.

* Bevisa att Ford-Fulkerson alltid genererar heltalsflöden om kantkapaciteterna är heltal!

Detta stämmer då: 
- 1. Flödet start som 0
- 2. Vi ökar flödet i varje augmentation med den minsta kapaciteten i den stig som hittas (fortfarande heltal)
- 3. Flödet ökas alltid med heltal, därför kommer flödet i varje kant alltid vara ett heltal.
- 4. Totala summan blir då heltal + heltal ... + heltal = heltal

* Vad händer med lösningarna som flödesalgoritmen ger om man ändrar i reduktionen så att kantkapaciteterna sätts till 2 istället för 1? 
