1. Formulera rekursionen (partDist i programmet) så kompakt som möjligt med matematisk notation.

2. Beräkna partDist("labd", "blad", x, y) för alla x och y mellan 0 och 4 och för in värdena i en matris M. Vad blir M?

   0 1 2 3 4 1 1 1 2 3 2 2 2 1 2 3 2 3 2 2 4 3 3 3 2

| x\y | 0   | 1   | 2   | 3   | 4   |
| --- | --- | --- | --- | --- | --- |
| 0   | 0   | 1   | 2   | 3   | 4   |
| 1   | 1   | 1   | 1   | 2   | 3   |
| 2   | 2   | 2   | 2   | 1   | 2   |
| 3   | 3   | 2   | 3   | 2   | 2   |
| 4   | 4   | 3   | 3   | 3   | 2   |

1. Vad är det alltså metoden partDist(w1, w2, x, y) beräknar?

2. Visa att tidskomplexiteten för Distance(w1, w2) är exponentiell i ordlängden. Du kan anta att w1 och w2 har samma längd.

3. Visa hur man kan spåra vilka editeringsoperationer som görs i den kortaste editeringsföljden från "labd" till "blad" genom att titta på matrisen M.

| x\y | 0   | 1       | 2       | 3       | 4       |
| --- | --- | ------- | ------- | ------- | ------- |
| 0   | 0   | **_1_** | 2       | 3       | 4       |
| 1   | 1   | 1       | **_1_** | 2       | 3       |
| 2   | 2   | 2       | 2       | **_1_** | 2       |
| 3   | 3   | 2       | 3       | **_2_** | 2       |
| 4   | 4   | 3       | 3       | 3       | **_2_** |

1. Visa med pseudokod hur rekursionen kan beräknas med dynamisk programmering, dvs hur en dynprogmatris M kan skapas. Vilken beräkningsordning är lämplig vid beräkning av M?

2. Analysera tidskomplexiteten för att bestämma editeringsavståndet mellan ett n-bokstavsord och ett m-bokstavsord med dynamisk programmering.

3. Beräkna dynprogmatrisen för editeringsavståndet mellan "labs" och "blad".

4. Vilken del av matriserna för "labd"-"blad" och "labs"-"blad" skiljer?

5. Allmänt sett, vilken del av matriserna för Y-X och Z-X skiljer när orden Y och Z har samma första p bokstäver?

```

```
