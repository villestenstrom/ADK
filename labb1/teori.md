
```1. Vilka är rollerna vid parprogrammering och vilka uppgifter har varje roll?```

  Drivver skriver koden och fokuserar på den tekniska implementationen, "hur"-delen.
  Observer/navigator granskar koden och fokuserar på det bredare perspektivet, såsom att peka ut möjliga framtida problem med koden. På detta sätt avlastas Driver:n. 

```2. Indexinformationen för ett ord (det vill säga i vilka teckenpositioner ordet förekommer i den stora texten) kan bli mycket stor. Hur bör positionerna lagras för att det ska bli effektivast, som text eller binärt (data streams i Java)? Bör indexinformationen lagras tillsammans med själva ordet eller på ett separat ställe?```

Det är klart effektivast att lagra positionerna binärt, då det kräver mindre utrymme än om det sparas som text, dessutom kan binärt läsas och skrivas snabbare. Indexinformationen bör lagras separat från själva order, då detta medför fördelen att sökningar och uppdateringar blir effektivare.

```3. I denna labb ska datastrukturen för konkordansen huvudsakligen ligga på fil, vilket betyder att sökningar görs i filen istället för som vanligt i internminnet. Det påverkar till exempel hur man representerar pekare (lämpligen som bytenummer i filen). Diskutera för- och nackdelar med olika implementationer av konkordansen.```
  
| Datastruktur    | Snabbhet                                                                                            | Minneskomplexitet                                                                                               | Enkelhet                                                                                           |
|-----------------|-----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| Binärt sökträd  | Snabb, O(log n)                                                                                     | Låg, varje not i trädet lagrar bara nyckeln och två pointers                                                    | Svårt att implementera en balansering av trädet                                                    |
| Sorterad array  | Snabb söktid (O(log n)), men insert/remove kan bli tidskrävande pga hela arrayen kan behöva flyttas | Låg, pga kontinuerlig allokering för arrays                                                                     | Lätt att implementera                                                                              |
| Hashtabell      | Snabb (O(n) för sökning, insättning och borttagning i bästa fall)                                   | Potentiellt hög om det sker många kollisionner                                                                  | Delvis enkel att implementera, men hantering av kollisioner och dynamisk skalning kan bli komplext |
| Trie            | Snabb (O(n))                                                                                        | Beror på om det finns många delade prefix eller inte (kan gå från mycket låg till mycket hög minneskomplexitet) | Mer komplex att implementera, särskilt som en filbaserad datastruktur.                             |
| Latmanshashning | Generellt snabb, men det beror på mängden kollisionner.                                             | Kan vara hög om hashtabellen är mycket större än antalet unika nycklar.                                         | Mycket enkelt att implementera; ingen behov av dynamisk skalning eller kollisionshantering.        |


```4. Ge exempel på minst 7 indata (dvs ord) som är lämpliga testfall i labb 1 och motivera varför.```
1. "algoritmens", då exempelkörningen visar att det ska finnas 12 förekomster är det bra att jämföra mot
2. "Algoritmens", för att se till att stora och små bokstäver hanteras lika
3. "" , testar hantering av tomma strings
4. ".", testar hantering av skiljetecken
5. "bil", testar att ord delas upp korrekt (vi vill inte få upp resultat för "biljett" till exempel)
6. "O (N²)", då vi på exempelkörningen ser att specialteckent '²' finns med i texten är det bra att kontrollera hanteringen av den
7. "även en", testar hanteringen av fler än ett ord som indata

```5. Konstruera en hashfunktion för latmanshashning och motivera varför den fungerar. Hashfunktionen ska ta ett ord som indata och returnera ett unikt hashvärde (heltal) baserat på ordets tre första bokstäver.```
```py
def hash_function(word):
    # Ser till att strängen alltid är minst tre karaktärer lång
    padded_word = word.ljust(3, ' ')
    
    # Hashar tre första tecknena
    hash_value = 0
    for i, char in enumerate(padded_word[:3]):
        # ord() returnerar unicode-värdet för karaktären
        hash_value += ord(char) * (256 ** i)
    
    return hash_value
```

Hashfunktionen fungerar då den alltid kommer ge samma hash för samma följd av tre första bokstäverna.
Genom att multiplicera unicode-värdet med (256 ** i) får vi olika hashvärden för samma tecken på olika positioner
