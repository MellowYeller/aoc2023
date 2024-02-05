import java.io.*;
import java.util.*;
import java.util.stream.*;

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
    try (BufferedReader in = new BufferedReader(new FileReader(input))) {
      String line = in.readLine();
      while (line != null && line.length() > 0) {
        if (this.showInput)
          System.out.println(line);

        // Code goes here
        List<Long> input = Arrays.stream(line.split(" +"))
          .map(Long::valueOf)
          .collect(Collectors.toList());
        ans1 += p1(input);
        ans2 += p2(input);
        

        line = in.readLine();
      }
    } catch (Exception e) {
      System.err.println("Encountered an error!");
      System.err.println(e);
      System.exit(1);
    }
  }

  private long p1(List<Long> l) {
    // I think the only values we have to look at are the final 2?
    if (l.parallelStream().allMatch(i -> i == 0))
      return 0;
    List<Long> newList = new ArrayList<>();
    for (int i=0; i<l.size()-1; i++)
      newList.add(l.get(i+1) - l.get(i));
    return l.get(l.size()-1) + p1(newList);
  }
 
  private long p2(List<Long> l) {
    // I think the only values we have to look at are the final 2?
    if (l.parallelStream().allMatch(i -> i == 0))
      return 0;
    List<Long> newList = new ArrayList<>();
    for (int i=0; i<l.size()-1; i++)
      newList.add(l.get(i+1) - l.get(i));
    return l.get(0) - p2(newList);
  }
  
  public static void main(String[] args) {

    // Example solution to test against
    final int solution1 = 114;
    final int solution2 = 2;

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
