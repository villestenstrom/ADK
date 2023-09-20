## 1. Formulera rekursionen (partDist i programmet) så kompakt som möjligt med matematisk notation.
partDist(w1, w2, w1len, w2len) = {
    w1len om w2len == 0
    w2len om w1len == 0
    min(partDist(w1, w2, w1len-1, w2len) + 1, partDist(w1, w2, w1len, w2len-1) + 1, partDist(w1, w2, w1len-1, w2len-1) + 1*(w1[w1len] != w2[w2len]))
}

## 2. Beräkna partDist("labd", "blad", x, y) för alla x och y mellan 0 och 4 och för in värdena i en matris M. Vad blir M?
```
0	1	2	3	4
1	1	1	2	3
2	2	2	1	2
3	2	3	2	2
4	3	3	3	2
```

## 3. Vad är det alltså metoden partDist(w1, w2, x, y) beräknar?
Hur många operationer som krävs för att gå från de x första bokstäverna i w1 till de y första bokstäverna i w2. Sista cellen visar därmed ur många operationer som krävs fö ratt transformera w1 till w2.

## 4. Visa att tidskomplexiteten för Distance(w1, w2) är exponentiell i ordlängden. Du kan anta att w1 och w2 har samma längd.
Funktionen partDist har 3 rekursiva anrop vilket ger ett rekursionträd med tre grenar. Djupet på trädet är n, längden på w1 och w2, vilket ger en tidskomplexitet på O(3^n).

## 5. Visa hur man kan spåra vilka editeringsoperationer som görs i den kortaste editeringsföljden från "labd" till "blad" genom att titta på matrisen M. 
För att spåra editeringsoperationerna kan vi gå från M[0][0] till M[4][4] (hela labd --> blad). Varje steg nedåt innebär att vi lägger till en bokstav, steg till höger innebär att vi tar bort en bokstav och steg diagonalt neråt innebär att vi byter ut alternativt behåller en bokstav. 

```
| **0** | **1** | 2 | 3 | 4 |
| 1 | 1 | **1** | 2 | 3 |
| 2 | 2 | 2 | **1** | 2 |
| 3 | 2 | 3 | **2** | 2 |
| 4 | 3 | 3 | 3 | **2** |
```

## 6. Visa med pseudokod hur rekursionen kan beräknas med dynamisk programmering, dvs hur en dynprogmatris M kan skapas. Vilken beräkningsordning är lämplig vid beräkning av M?
1. Skapa matris M med storlek (w1len + 1) * (w2len + 1)
2. Om M[w1len][w2len] != 0 returnera M[w1len][w2len]
3. Om w1len == 0 returnera w2len
4. Om w2len == 0 returnera w1len
5. 

## 7. Analysera tidskomplexiteten för att bestämma editeringsavståndet mellan ett n-bokstavsord och ett m-bokstavsord med dynamisk programmering.


## 8. Beräkna dynprogmatrisen för editeringsavståndet mellan "labs" och "blad".
```
0	1	2	3	4
1	1	1	2	3
2	2	2	1	2
3	2	3	2	2
4	3	3	3	3
```

## 9. Vilken del av matriserna för "labd"-"blad" och "labs"-"blad" skiljer?
Det enda som skiljer är den sista cellen som nu är 3 instället för 2. Det är logiskt då det enda som är skillnaden mellan de två matriserna är att den ena har "labd" och den andra har "labs". Allt är samma förutom den sista bokstaven i ordet vilket endast den sista cellen påverkas av.

## 10. Allmänt sett, vilken del av matriserna för Y-X och Z-X skiljer när orden Y och Z har samma första p bokstäver?
Alla celler från M[p+1][p+1] till M[ylen][zlen] kommer att förändras  ....


