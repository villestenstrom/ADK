
/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;
  int closestDistance = -1;
  char[] latestWord = {' '};
  int longestWord = 40;
  int[][] memo = new int[40][40];

  int partDist(char[] w1, char[] w2, int w1len, int w2len, int start) {

    for (int i = 0; i <= w1len; i++) {
      memo[i][0] = i;
    }
    for (int j = 0; j <= w2len; j++) {
      memo[0][j] = j;
    }

    start = Math.max(1, start);
    for (int i = 1; i <= w1len; i++) {
      for (int j = start; j <= w2len; j++) {
        int cost = w1[i - 1] == w2[j - 1] ? 0 : 1;
        int addLetter = memo[i - 1][j] + 1;
        int deleteLetter = memo[i][j - 1] + 1;
        int changeLetter = memo[i - 1][j - 1] + cost;
        memo[i][j] = Math.min(Math.min(addLetter, deleteLetter),
            changeLetter);
      }
    }
    return memo[w1len][w2len];
  }

  int distance(String w1, String w2, int start) {
    return partDist(w1.toCharArray(), w2.toCharArray(), w1.length(), w2.length(), start);
  }

  public ClosestWords(String w, List<String> wordList) {
    int differentLettersIndex = 0;

    for (String s : wordList) {
      char[] sChar = s.toCharArray();
      if (closestDistance != -1) {
        int lengthDifference = Math.abs(s.length() - w.length());
        if (lengthDifference > closestDistance) {
          continue;
        }

        differentLettersIndex = 1;
        for (int i = 0; i < Math.min(sChar.length, latestWord.length); i++) {
          if (sChar[i] != latestWord[i]) {
            break;
          }
          differentLettersIndex++;
        }

      }
      latestWord = sChar;

      int dist = distance(w, s, differentLettersIndex);
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

  public LinkedList<String> getClosestWords() {
    return closestWords;
  }
}