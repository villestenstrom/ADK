
/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;
  int closestDistance = -1;
  String latestWord = "";

  int partDist(String w1, String w2, int w1len, int w2len, int[][] memo, int start) {

    for (int i = 0; i <= w1len; i++) {
      for (int j = start; j <= w2len; j++) {

        if (memo[i][j] != 0) {
          continue;
        } else if (i == 0) {
          memo[i][j] = j;
        } else if (j == 0) {
          memo[i][j] = i;
        } else {
          int addLetter = memo[i - 1][j] + 1;
          int deleteLetter = memo[i][j - 1] + 1;
          int replaceCost = (w1.charAt(i - 1) == w2.charAt(j - 1)) ? 0 : 1;
          int replaceLetter = memo[i - 1][j - 1] + replaceCost;

          memo[i][j] = Math.min(Math.min(addLetter, deleteLetter), replaceLetter);
        }
      }
    }
    return memo[w1len][w2len];
  }

  int distance(String w1, String w2, int[][] memo, int start) {
    return partDist(w1, w2, w1.length(), w2.length(), memo, start);
  }

  public ClosestWords(String w, List<String> wordList) {
    int longestWord = 21;
    int[] zeroArray = new int[longestWord];
    int[][] memo = new int[w.length() + 1][longestWord];
    int differentLettersIndex = 0;
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

        for (int i = 0; i < w.length() + 1; i++) {
          System.arraycopy(zeroArray, 0, memo[i], differentLettersIndex, longestWord - differentLettersIndex);
        }

      }

      int dist = distance(w, s, memo, differentLettersIndex);
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
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
