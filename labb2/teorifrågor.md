# 1.

# 2. ```
0	1	2	3	4
1	1	1	2	3
2	2	2	1	2
3	2	3	2	2
4	3	3	3	2
```

# 3. Hur många operationer som krävs för att gå från de x första bokstäverna i w1 till de y första bokstäverna i w2. Sista cellen visar därmed ur många operationer som krävs fö ratt transformera w1 till w2.

# 4. Funktionen partDist har 3 rekursiva anrop vilket ger ett rekursionträd med tre grenar. Djupet på trädet är n, längden på w1 och w2, vilket ger en tidskomplexitet på O(3^n).

# 5. | 0 | **1** | 2 | 3 | 4 |
| 1 | 1 | **1** | 2 | 3 |
| 2 | 2 | 2 | **1** | 2 |
| 3 | 2 | 3 | **2** | 2 |
| 4 | 3 | 3 | 3 | **2** |

# 6. 
1. Skapa matris M med storlek (w1len + 1) * (w2len + 1)
2. Om M[w1len][w2len] != 0 returnera M[w1len][w2len]
