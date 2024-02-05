import java.io.*;
import java.util.*;

public class Solution {
  public String input;
  public long ans1 = 0;
  public long ans2 = 0;
  private boolean showInput = false;

  public Solution(String input) {
    this.input = input;
  }
  public Solution(String input, boolean showInput) {
    this.input = input;
    this.showInput = showInput;
  }

  public void solve() {
    List<CamelHand> hands = new ArrayList<>();
    List<CamelHand2> hands2 = new ArrayList<>();
    try (BufferedReader in = new BufferedReader(new FileReader(input))) {
      String line = in.readLine();
      while (line != null && line.length() > 0) {
        if (this.showInput)
          System.out.println(line);

        // Code goes here
        hands.add(new CamelHand(line));
        hands2.add(new CamelHand2(line));

        line = in.readLine();
      }
    } catch (Exception e) {
      System.err.println("Encountered an error!");
      System.err.println(e);
      e.printStackTrace();
      System.exit(1);
    }
    // Part 1
    Collections.sort(hands);
    for (int i=0; i<hands.size(); i++)
      ans1 += hands.get(i).bid * (i+1);
    
    // Part 2
    Collections.sort(hands2);
    for (int i=0; i<hands2.size(); i++)
      ans2 += hands2.get(i).bid * (i+1);
  }
  
  public static void main(String[] args) {

    // Example solution to test against
    final long solution1 = 6440;
    final long solution2 = 5905;

    System.out.println("AoC Solution\n");

    System.out.println("Testing example...");
    Solution example = new Solution("example.txt", true);

    example.solve();

    System.out.println("Part 1:");
    if (solution1 == example.ans1)
      System.out.println("Passed.");
    else {
      System.out.println("Failed.");
      System.out.println("Expected: " + solution1);
      System.out.println("Found: " + example.ans1);
    }

    System.out.println();

    if (solution2 != -1) {
      System.out.println("Part 2:");
      if (solution2 == example.ans2)
        System.out.println("Passed.");
      else {
        System.out.println("Failed.");
        System.out.println("Expected: " + solution2);
        System.out.println("Found: " + example.ans2);
      }

      System.out.println();


    }

    if (solution1 == example.ans1 && (solution2 == -1 || solution2 == example.ans2)) {

      System.out.println("Test passed. Running for real...");
      Solution solution = new Solution("input.txt");

      solution.solve();

      System.out.println("Part 1:");
      System.out.println(solution.ans1);
      System.out.println();

      if (solution2 != -1) {
        System.out.println("Part 2:");
        System.out.println(solution.ans2);
        System.out.println();
      }
    }

  }
  
}

class CamelHand2 implements Comparable<CamelHand2> {
  char[] hand;
  int bid;
  int typeScore;

  // Example hand format:
  // 32T3K 765
  public CamelHand2(String line) {
    hand = new char[5];
    String[] info = line.split(" +");
    this.parseHand(info[0]);

    bid = Integer.parseInt(info[1]);
  }

  private void parseHand(String h) {
    int i = 0;
    for (char c : h.toCharArray()) {
      int offset = 0;
      switch (c) {
        case 'T':
          offset = 1;
          break;
        case 'J':
          offset = -8;
          break;
        case 'Q':
          offset = 2;
          break;
        case 'K':
          offset = 3;
          break;
        case 'A':
          offset = 4;
          break;
        default:
          offset = 0;
      }
      if (offset != 0)
        hand[i++] = (char)('9' + offset);
      else
        hand[i++] = c;
    }

    Map<Character, Integer> map = new HashMap<Character, Integer>();
    map.put(' ', 0);
    int jokerCount = 0;
    for (char c : hand)
      if (c == '1') // is joker
        jokerCount++;
    else if (map.containsKey(c))
        map.put(c, map.get(c)+1);
      else
        map.put(c, 1);
    List<Integer> counts = new ArrayList<>(map.values());
    Collections.sort(counts, Collections.reverseOrder());
    if (jokerCount > 0) {
      // Try to make 4 or 5 of a kind
      if (counts.get(0) + jokerCount > 3)
        if (counts.get(0) + jokerCount == 5)
          typeScore = 7;
        else  
          typeScore = 6;
      // Try to make a full house
      else if (isTwoPair(counts) || counts.contains(2) && jokerCount > 1)
        typeScore = 5;
      // Try to make three of a kind
      else if (counts.contains(2) || jokerCount > 1)
        typeScore = 4;
      // I don't see a situation where we make a two pair
      // If we have a joker at all, we can at least make one pair
      else
        typeScore = 2;

    } else {
      if (counts.contains(5))
        typeScore = 7;
      else if (counts.contains(4))
        typeScore = 6;
      else if (counts.contains(3) && counts.contains(2))
        typeScore = 5;
      else if (counts.contains(3))
        typeScore = 4;
      else if (isTwoPair(counts))
        typeScore = 3;
      else if (counts.contains(2))
        typeScore = 2;
      else
        typeScore = 1;
    }
  }

  private boolean isTwoPair(Collection<Integer> counts) {
    int twos = 0;
    for (Integer i : counts)
      if (i == 2) twos++;
    return twos == 2;
  }

  @Override
  public int compareTo(CamelHand2 o) {
    int res = this.typeScore - o.typeScore;
    if (res != 0)
      return res;
    for (int i=0; i<5; i++) {
      res = this.hand[i] - o.hand[i];
      if (res != 0)
        return res;
    }
    return res;
  }
}

class CamelHand implements Comparable<CamelHand> {
  char[] hand;
  int bid;
  int typeScore;

  // Example hand format:
  // 32T3K 765
  public CamelHand(String line) {
    hand = new char[5];
    String[] info = line.split(" +");
    this.parseHand(info[0]);

    bid = Integer.parseInt(info[1]);
  }

  private void parseHand(String h) {
    int i = 0;
    for (char c : h.toCharArray()) {
      int offset = 0;
      switch (c) {
        case 'T':
          offset = 1;
          break;
        case 'J':
          offset = 2;
          break;
        case 'Q':
          offset = 3;
          break;
        case 'K':
          offset = 4;
          break;
        case 'A':
          offset = 5;
          break;
        default:
          offset = 0;
      }
      if (offset != 0)
        hand[i++] = (char)('9' + offset);
      else
        hand[i++] = c;
    }

    Map<Character, Integer> map = new HashMap<Character, Integer>();
    for (char c : hand)
      if (map.containsKey(c))
        map.put(c, map.get(c)+1);
      else
        map.put(c, 1);
    Collection<Integer> counts = map.values();
    // Five of a kind
    if (counts.contains(5))
      typeScore = 7;
    else if (counts.contains(4))
      typeScore = 6;
    else if (counts.contains(3) && counts.contains(2))
      typeScore = 5;
    else if (counts.contains(3))
      typeScore = 4;
    else if (isTwoPair(counts))
      typeScore = 3;
    else if (counts.contains(2))
      typeScore = 2;
    else
      typeScore = 1;
  }

  private boolean isTwoPair(Collection<Integer> counts) {
    int twos = 0;
    for (Integer i : counts)
      if (i == 2) twos++;
    return twos == 2;
  }

  @Override
  public int compareTo(CamelHand o) {
    int res = this.typeScore - o.typeScore;
    if (res != 0)
      return res;
    for (int i=0; i<5; i++) {
      res = this.hand[i] - o.hand[i];
      if (res != 0)
        return res;
    }
    return res;
  }

}
