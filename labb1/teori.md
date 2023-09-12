`1. Vilka är rollerna vid parprogrammering och vilka uppgifter har varje roll?`

Drivver skriver koden och fokuserar på den tekniska implementationen, "hur"-delen.
Observer/navigator granskar koden och fokuserar på det bredare perspektivet, såsom att peka ut möjliga framtida problem med koden. På detta sätt avlastas Driver:n.

`2. Indexinformationen för ett ord (det vill säga i vilka teckenpositioner ordet förekommer i den stora texten) kan bli mycket stor. Hur bör positionerna lagras för att det ska bli effektivast, som text eller binärt (data streams i Java)? Bör indexinformationen lagras tillsammans med själva ordet eller på ett separat ställe?`

Det är klart effektivast att lagra positionerna binärt, då det kräver mindre utrymme än om det sparas som text. Indexinformationen bör lagras separat från själva ordet, främst för att det ger mer flexibiletet då vi kan uppdatera indexinformationen utan att ändra i den ursprungliga datan. Detta kan däremot ge en längre söktid jämfört med att lagra index och ordet tillsammans. Men nackdelen med att datan blir mindre flexibel gör att det ändå generellt är bäst att spara datan på separata ställen.

`3. I denna labb ska datastrukturen för konkordansen huvudsakligen ligga på fil, vilket betyder att sökningar görs i filen istället för som vanligt i internminnet. Det påverkar till exempel hur man representerar pekare (lämpligen som bytenummer i filen). Diskutera för- och nackdelar med olika implementationer av konkordansen.`

| Datastruktur    | Snabbhet                                                                                                                                                                                                                        | Minneskomplexitet                                                                                                                                                                                                                                                                                                              | Enkelhet                                                                                                       |
| --------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------- |
| Binärt sökträd  | För det mesta snabb, balanserat träd ger O(log n) men blir i värsta fall O(n) om det är ett helt obalanserat träd (pga kräver en seek + read operation per nod).                                                                | Låg, varje not i trädet lagrar bara nyckeln och två pointers                                                                                                                                                                                                                                                                   | Svårt att implementera en balansering av trädet                                                                |
| Sorterad array  | Snabb söktid (O(log n)) genom binärsökning, men insert/remove kan bli tidskrävande pga hela arrayen kan behöva flyttas                                                                                                          | Låg, eftersom varje element bara lagras en gång (O(n))                                                                                                                                                                                                                                                                         | Väldigt lätt att implementera                                                                                  |
| Hashtabell      | Sökning i en hashtabell blir O(1) utan kollisioner (alltså bara en read operation för att hitta elementet + seek för att få positionen). Kollissioner försämrar snabbheten, men i de flesta fall avviker det inte mycket.       | Potentiellt hög om det sker många kollisionner                                                                                                                                                                                                                                                                                 | Delvis enkel att implementera, men hantering av kollisioner och dynamisk skalning kan bli komplext             |
| Trie            | Väldigt snabb vid strängsökningar, krävs lika många steg som tecken i ordet man söker efter ( O(L), där L = längd på ordet )                                                                                                    | Beror på om det finns många delade prefix eller inte (kan gå från mycket låg till mycket hög minneskomplexitet). Potentiellt kan ju en nod i trädet ha ett barn för varje bokstav i alfabetet, så det blir ganska ineffektivt när minne behöver allokeras för att varje nod potentiellt ska kunna ha 29 barn (svenskt alfabet) | Mer komplex att implementera, särskilt som en filbaserad datastruktur.                                         |
| Latmanshashning | Vi kommer till rätt hash på O(1), sedan görs en binärsökning på O(log n) tid för att hitta rätt ord "mellan hashningarna". När vi väl hittat ordet görs en linjärsökning i O(n) genom alla index för ordets förekomst i texten. | Då endast de tre första bokstäverna i orden hashas behöver vi inte dynamiskt allokera minne, istället allokerar vi utrymme för 29^3 st prefix, vilket vi då kan allokera direkt i O(1).                                                                                                                                        | Hashtabellen och tillhörande lista är lätt att lagra, men indexfilen behöver läsas och uppdateras linjärt O(n) |

`4. Ge exempel på minst 7 indata (dvs ord) som är lämpliga testfall i labb 1 och motivera varför.`

1. "algoritmens", då exempelkörningen visar att det ska finnas 12 förekomster är det bra att jämföra mot
2. "Algoritmens", för att se till att stora och små bokstäver hanteras lika
3. "" , testar hantering av tomma strings
4. ".", testar hantering av skiljetecken
5. "bil", testar att ord delas upp korrekt (vi vill inte få upp resultat för "biljett" till exempel)
6. "O (N²)", då vi på exempelkörningen ser att specialteckent '²' finns med i texten är det bra att kontrollera hanteringen av den
7. "även en", testar hanteringen av fler än ett ord som indata

`5. Konstruera en hashfunktion för latmanshashning och motivera varför den fungerar. Hashfunktionen ska ta ett ord som indata och returnera ett unikt hashvärde (heltal) baserat på ordets tre första bokstäver.`

```py
def hash_function(word):
    # Init
    alph = 'abcdefghijklmnopqrstuvwxyzåäö'
    # Ser till att strängen alltid är minst tre karaktärer lång och försäkrar case-insensitivity
    word = word.lower().ljust(3, ' ')

    # Hashar tre första tecknena
    hash_value = 0
    for i, char in enumerate(word[:3]):
        char_value = alph.index(char) + 1
        # ord() returnerar unicode-värdet för karaktären
        hash_value += char_value * (30 ** i)

    return hash_value
```

Motivering: Funktionen tar ett ord (word) som den fyller ut med blanksteg ifall sökordet är under tre tecken långt (ex. "ål" blir "ål ") samt gör stora boktsäver till små.
Hashfunktionen tittar sedan på en bokstav i taget av de tre första, och för varje tecken skapas ett hashvärde genom att ta tecknets index (' ' = 0, a = 1...)
och multiplicera det med en exponent av 30 beroende på tecknets position (så 30^1, 30^2, 30^3). Detta är för att se till att det inte blir några överlapp där
olika följd av ord kan ge samma hash. T.ex. hade "dog" och "god" genererat samma hashvärde om hashen inte påverkades av ordning på tecknena.
Enumerate-loopen körs tre gånger och adderar ihop varje teckens hashvärde till "hash_value", som sedan returneras.
