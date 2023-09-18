
/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;
  int closestDistance = -1;
  String latestWord = "";

  int partDist(String w1, String w2, int w1len, int w2len, int[][] memo) {

    int memo_value = memo[w1len][w2len];
    if (memo_value != 0)
      return memo_value;

    if (w1len == 0)
      return w2len;
    if (w2len == 0)
      return w1len;

    int addLetter = partDist(w1, w2, w1len - 1, w2len, memo) + 1;
    int deleteLetter = partDist(w1, w2, w1len, w2len - 1, memo) + 1;
    int replaceLetter = partDist(w1, w2, w1len - 1, w2len - 1, memo) +
        (w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);
  
    int res = Math.min(Math.min(addLetter, deleteLetter), replaceLetter);
    memo[w1len][w2len] = res;
    return res;
  }

  int distance(String w1, String w2, int[][] memo) {
    return partDist(w1, w2, w1.length(), w2.length(), memo);
  }

  public ClosestWords(String w, List<String> wordList) {
    int longestWord = 35;
    int[][] memo = new int[w.length() + 1][longestWord];
    int differentLettersIndex = 0;
    closestWords = new LinkedList<String>();
    for (String s : wordList) {
      if (closestDistance != -1) {
        int lengthDifference = Math.abs(s.length() - w.length());
        if (lengthDifference > closestDistance) {
          continue;
        }
        

        differentLettersIndex = 0;
        for (int i = 0; i < Math.min(s.length(), latestWord.length()); i++) {
          if (s.charAt(i) != latestWord.charAt(i)) {
            break;
          }
          differentLettersIndex++;
        }

      }
      latestWord = s;

      for (int i = 0; i < w.length() + 1; i++) {
        for (int j = differentLettersIndex; j < longestWord; j++) {
          memo[i][j] = 0;
        }
      }

      int dist = distance(w, s, memo);
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords.clear();
        closestWords.add(s);
      } else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}
