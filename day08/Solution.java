import java.io.*;
import java.util.*;

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
    char[] directions = new char[0];
    Map<String, Node> nodes = new HashMap<>();
    List<Node> simul = new ArrayList<>();
    Set<Node> ends = new HashSet<>();
    try (BufferedReader in = new BufferedReader(new FileReader(input))) {
      String line = in.readLine();
      if (this.showInput)
        System.out.println(line);
      directions = line.toCharArray();
      line = in.readLine(); //clear blank line
      if (this.showInput)
        System.out.println();

      line = in.readLine();
      while (line != null && line.length() > 0) {
        if (this.showInput)
          System.out.println(line);

        // Code goes here
        line = line.replaceAll("[=\\(\\),]", "");
        String[] info = line.split(" +");

        for (int i=0; i<3; i++)
          if (!nodes.containsKey(info[i]))
            nodes.put(info[i], new Node(info[i].equals("ZZZ"), info[i]));

        Node n = nodes.get(info[0]);
        n.left = nodes.get(info[1]);
        n.right = nodes.get(info[2]);

        if (info[0].charAt(2) == 'A')
          simul.add(n);
        else if (info[0].charAt(2) == 'Z')
          ends.add(n);

        line = in.readLine();
      }
    } catch (Exception e) {
      System.err.println("Encountered an error!");
      System.err.println(e);
      System.exit(1);
    }

    // Part one
    int i = 0;
    Node node = nodes.get("AAA");
    while (!node.isEnd) {
      if (directions[i] == 'R')
        node = node.right;
      else
        node = node.left;
      i = (i+1) % directions.length;
      ans1++;
    }
    
    // Test part 2
    long[] offsets = new long[simul.size()];
    long[] repeats = new long[simul.size()];
    for (int nodeIndex = 0; nodeIndex<simul.size(); nodeIndex++) {
      offsets[nodeIndex] = Long.MAX_VALUE;
      Set<String> set = new HashSet<>();
      Node curr = simul.get(nodeIndex);
      long exitIndex = -1;
      int directionIndex = 0;
      int c = 0;
      while (!set.contains("n" + curr.name + "i" + directionIndex)) {
        set.add("n" + curr.name + "i" + directionIndex);
        if (curr.name.charAt(2) == 'Z') {
          System.out.println("Found an exit: " + curr.name);
          System.out.println("At iteration = " + c + ", index = " + directionIndex);
          exitIndex = directionIndex;
          offsets[nodeIndex] = Math.min(c, offsets[nodeIndex]);
        }
        curr = (directions[directionIndex] == 'R') ? curr.right : curr.left;
        directionIndex = (directionIndex+1) % directions.length;
        c++;
      }
      System.out.println("Found " + nodeIndex + "'s loop in " + c + " iterations!");
      repeats[nodeIndex] = c-1;
    }

    
    // Part two
    if (repeats.length > 1)
      ans2 = lcm(offsets);
    else
      ans2 = offsets[0];

    // Part 2 old
    /*
    boolean done = false;
    int directionIndex = 0;
    while (!done) {
      done = true;
      for (int nodeIndex=0; nodeIndex<simul.size(); nodeIndex++) {
        Node current = simul.get(nodeIndex);
        Node next;
        if (directions[directionIndex] == 'R')
          next = current.right;
        else
          next = current.left;
        if (!ends.contains(next))
          done = false;
        simul.set(nodeIndex, next);
      }
      directionIndex = (directionIndex+1) % directions.length;
      ans2++;
      if (ans2 % 100_000_000 == 0)
        System.out.println(ans2);
    }
    */
  }

  public static long lcm(long... vals) {
    if (vals.length == 0) return 0;
    if (vals.length == 1) return vals[0];
    long res = lcm(vals[0], vals[1]);
    for (int i=2; i<vals.length; i++)
      res = lcm(vals[i], res);
    return res;
  }


  public static long lcm(long a, long b) {
    long gcd = gcd(a,b);
    long res = a / gcd;
    return b * res;
  }

  public static long gcd(long... vals) {
    if (vals.length == 0) return 0;
    if (vals.length == 1) return vals[0];
    long res = gcd(vals[0], vals[1]);
    for (int i=2; i<vals.length; i++)
      res = gcd(vals[i], res);

    return res;
  }

  public static long gcd(long a, long b) {
    /*
    while (a != b) {
      if (a > b)
        a = a - b;
      else
        b = b - a;
    }
    return a;
    */
    if (b == 0) return a;
    return gcd(b, a % b);
  }
  
  public static void main(String[] args) {

    // Example solution to test against
    final int solution1 = 6;
    final long solution2 = 6;

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
  
class Node {
  String name;
  boolean isEnd;
  Node left;
  Node right;

  public Node() { }

  public Node(boolean isEnd) {
    this.isEnd = isEnd;
  }

  public Node(boolean isEnd, String name) {
    this.isEnd = isEnd;
    this.name = name;
  }


  public Node(Node left, Node right) {
    this.left = left;
    this.right = right;
    this.isEnd = false;
  }
}
