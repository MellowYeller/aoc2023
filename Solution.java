import java.io.*;
import java.util.*;

public class Solution {
  public String input;
  public int ans1 = 0;
  public int ans2 = 0;
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

        line = in.readLine();
      }
    } catch (Exception e) {
      System.err.println("Encountered an error!");
      System.err.println(e);
      System.exit(1);
    }
  }
  
  public static void main(String[] args) {

    // Example solution to test against
    final int solution1 = -1;
    final int solution2 = -1;

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
