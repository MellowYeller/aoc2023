import java.io.*;
import java.util.*;
import java.math.*;

public class Solution {
  public String input;
  public int ans1 = 0;
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
    List<int[]> races = new ArrayList<>();
    // long time = 0;
    // long distance = 0;
    StringBuilder t = new StringBuilder();
    StringBuilder d = new StringBuilder();
    try (BufferedReader in = new BufferedReader(new FileReader(input))) {
      String line = in.readLine();
      // Code goes here
      String[] vals = line.split(":")[1].trim().split("\\s+"); 
      for (String val : vals)
        races.add(new int[] { Integer.parseInt(val), 0 });

      for (String val : vals)
        for (char c : val.toCharArray())
          t.append(c);

      line = in.readLine();
      vals = line.split(":")[1].trim().split("\\s+"); 
      int i=0;
      for (String val : vals)
        races.get(i++)[1] = Integer.parseInt(val);

      for (String val : vals)
        for (char c : val.toCharArray())
          d.append(c);

    } catch (Exception e) {
      System.err.println("Encountered an error!");
      System.err.println(e);
      System.exit(1);
    }

    ans1 = 1;
    for (int[] race : races) {
      ans1 *= parseRace(race);
    }

    ans2 = 0;
    BigInteger time = new BigInteger(t.toString());
    BigInteger distance = new BigInteger(d.toString());
    System.out.println("Time: " + time);
    System.out.println("Distance: " + distance);
    BigInteger[] finalRace = new BigInteger[] { time, distance };
    // ans2 = parseLongRace(finalRace);
    ans2 = pt2(finalRace);

  }
  private long parseRace(int[] race) {
    int count = 0;
    for (int i=0; i<race[0]; i++) {
      int dist = i * (race[0] - i);
      if (dist > race[1]) count++;
    }
    return count;
  }

  private long parseLongRace(BigInteger[] race) {
    int count = 0;
    for (BigInteger i=BigInteger.ZERO; race[0].compareTo(i) > 0; i.add(BigInteger.ONE)) {
      BigInteger dist = race[0].subtract(i);
      dist = dist.multiply(i);
      if (dist.compareTo(race[1]) > 1) count++;
    }
    return count;
  }

  private long pt2(BigInteger[] race) {
    long start = find(race, true, 0, race[0].longValue());
    long end = find(race, false, 0, race[0].longValue());
    System.out.println("Start: " + start);
    System.out.println("End: " + end);
    return end - start + 1; // Plus one because inclusive
  }
  private long find(BigInteger[] race, boolean first, long start, long end) {
    long i = (start + end) / 2;
    if (i == start && i == end)
      return i;
    BigInteger val = distance(race[0], i);
    BigInteger valPlusOne = distance(race[0], i+1);
    BigInteger valMinusOne = distance(race[0], i-1);
    if (first == true) {
      if (val.compareTo(race[1]) > 0 && (i-1 < 1 || valMinusOne.compareTo(race[1]) <= 0))
        return i;
      // If the previous value wins the race, search backwards
      // If the previous value is bigger than the current value, search backwards
      else if (i-1 > 0 && (race[1].compareTo(valMinusOne) < 0 || val.compareTo(valMinusOne) < 0)) 
        return find(race, first, start, i-1);
      // If the current value doesn't win the race and the next value is bigger, go forwards
      //else if (val.compareTo(race[1]) < 0 && race[0].compareTo(new BigInteger((i+1) + "")) >= 0 && val.compareTo(valPlusOne) < 0)
      else
        return find(race, first, i+1, end);
      // else
      //   return i;
    }
    else {
      if (val.compareTo(race[1]) > 0 && (race[0].compareTo(new BigInteger((i+1) + "")) < 0 || valPlusOne.compareTo(race[1]) <= 0))
        return i;
      // If the next value wins the race, search forwards
      // If the next value doesn't win the race but is bigger, search forwards
      if (race[0].compareTo(new BigInteger((i+1) + "")) >= 0 && (race[1].compareTo(valPlusOne) < 0 || val.compareTo(valPlusOne) < 0))
        return find(race, first, i+1, end);
      // If the current value wins the race, and so does the previous value, search backwards
      // else if (val.compareTo(race[1]) > 0 && i-1 > 0 && valMinusOne.compareTo(race[1]) > 0)
      else
        return find(race, first, start, i-1);
      // else
      //   return i;
    }
  }

  private BigInteger distance(BigInteger totalTime, long heldTime) {
    BigInteger i = new BigInteger(heldTime + "");
    BigInteger res = totalTime.subtract(i);
    res = res.multiply(i);
    return res;
  }
  
  public static void main(String[] args) {

    // Example solution to test against
    final int solution1 = 4 * 8 * 9;
    final int solution2 = 71503;

    System.out.println("AoC Solution\n");

    System.out.println("Testing example...");
    Solution example = new Solution("example.txt", true);

    example.solve();

    System.out.println("Part 1:");
    System.out.println("Expected: " + solution1);
    System.out.println("Found: " + example.ans1);
    System.out.println();

    System.out.println("Part 2:");
    System.out.println("Expected: " + solution2);
    System.out.println("Found: " + example.ans2);
    System.out.println();

    System.out.println("Test complete. Running for real...");
    Solution solution = new Solution("input.txt");

    solution.solve();

    System.out.println("Part 1:");
    System.out.println(solution.ans1);
    System.out.println();

    System.out.println("Part 2:");
    System.out.println(solution.ans2);
    System.out.println();

  }
  
}
