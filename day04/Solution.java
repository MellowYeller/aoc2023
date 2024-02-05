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
    LinkedList<Integer> ll = new LinkedList<>();
    try (BufferedReader in = new BufferedReader(new FileReader(input))) {
      String line = in.readLine();
      while (line != null && line.length() > 0) {
        if (this.showInput)
          System.out.println(line);

        // Code goes here
        int val = getScratchcardValue(line);
        ans1 += (val == 0) ? 0 : ((int)Math.pow(2, val-1));
        Integer n = ll.pollFirst();
        n = (n == null) ? 0 : n;
        ans2 += 1 + n;

        for (int i = 0; i<val; i++) {
          if (i < ll.size())
            ll.set(i, ll.get(i) + 1 + n);
          else
            ll.offer(1+n);
        }

        line = in.readLine();
      }
    } catch (Exception e) {
      System.err.println("Encountered an error!");
      System.err.println(e);
      System.exit(1);
    }
  }

  private int getScratchcardValue(String s) {
    String[] temp = s.split(":")[1].split("\\|");
    String[] winning = temp[0].trim().split("\\s+");
    String[] have = temp[1].trim().split("\\s+");

    Set<Integer> set = new HashSet<>();
    for (String num : winning)
      set.add(Integer.parseInt(num));

    int count = 0;
    for (String num : have)
      if (set.contains(Integer.parseInt(num)))
        count++;

    return count;
  }
  
  public static void main(String[] args) {

    // Example solution to test against
    final int solution1 = 13;
    final int solution2 = 30;

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
