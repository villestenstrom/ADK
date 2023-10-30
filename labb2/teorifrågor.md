## 1. Formulera rekursionen (partDist i programmet) så kompakt som möjligt med matematisk notation.


## 2. Beräkna partDist("labd", "blad", x, y) för alla x och y mellan 0 och 4 och för in värdena i en matris M. Vad blir M?
```
0	1	2	3	4
1	1	1	2	3
2	2	2	1	2
3	2	3	2	2
4	3	3	3	2
```

## 3. Vad är det alltså metoden partDist(w1, w2, x, y) beräknar?
Hur många operationer som krävs för att gå från de x första bokstäverna i w1 till de y första bokstäverna i w2. Sista cellen visar därmed ur många operationer som krävs för att transformera w1 till w2.

## 4. Visa att tidskomplexiteten för Distance(w1, w2) är exponentiell i ordlängden. Du kan anta att w1 och w2 har samma längd.
Funktionen partDist har 3 rekursiva anrop vilket ger ett rekursionträd med tre grenar. Antalet noder i varje gren blir därför 3^x. Djupet på trädet är n, längden på w1 och w2, vilket ger en tidskomplexitet på O(3^n). 

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
partDist(w1, w2, w1len, w2len)
    Skapa 2D-array M[w1len+1][w2len+1]

    for i = 0 till w1len
        for j = 0 till w2len
            if i == 0
                M[i][j] = j
            else if j == 0
                M[i][j] = i
            else
                cost = 0 om w1[i] == w2[j] annars 1
                M[i][j] = min(M[i-1][j] + 1, M[i][j-1] + 1, M[i-1][j-1] + cost)

    return M[w1len][w2len]


Det är lämpligt att gå radvis från vänster till höger eftersom att Java är uppbyggt på det sättet.

## 7. Analysera tidskomplexiteten för att bestämma editeringsavståndet mellan ett n-bokstavsord och ett m-bokstavsord med dynamisk programmering.

Skapa matrisen: O(n*m)

Fylla matrisen: O(n*m)

För att bestämma för ett n-bokstavsord och ett m-bokstavsord blir det alltså O(n*m) + O(n*m) = O(n*m)


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
Alla celler från M[p+1][p+1] till M[ylen][zlen] kommer att förändras. Detta då alla celler innan (M[0][0] till M[p][p]) beskriver hur många operationer som krävs för att gå från de p första bokstäverna i Y till de p första bokstäverna i Z (och dessa är ju samma).


