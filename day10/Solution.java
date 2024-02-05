import java.io.*;
import java.util.*;

public class Solution {
  public String input;
  public int ans1 = 0;
  public int ans2 = 0;
  private boolean showInput = false;
  private List<char[]> grid;
  private Map<Character, String> legend;

  public Solution(String input) {
    this.input = input;
    this.init();
  }
  public Solution(String input, boolean showInput) {
    this.input = input;
    this.showInput = showInput;
    this.init();
  }
  private void init() {
    grid = new ArrayList<char[]>();
    legend = new HashMap<>();
    legend.put('|', "NS");
    legend.put('-', "EW");
    legend.put('L', "NE");
    legend.put('J', "NW");
    legend.put('7', "SW");
    legend.put('F', "SE");
    legend.put('.', "G");
    legend.put('S', "?");
  }

  public void solve() {
    // Define map symbols
    int[] start = new int[]{-1,-1};
    try (BufferedReader in = new BufferedReader(new FileReader(input))) {
      String line = in.readLine();
      while (line != null && line.length() > 0) {
        if (this.showInput)
          System.out.println(line);

        // Code goes here
        
        // Add to grid
        grid.add(line.toCharArray());

        // Find starting location
        if (start[0] == -1) {
          int sLocation = line.indexOf('S');
          if(sLocation != -1) {
            start[0] = grid.size()-1;
            start[1] = sLocation;
           }
        }

        line = in.readLine();
      }
    } catch (Exception e) {
      System.err.println("Encountered an error!");
      System.err.println(e);
      System.exit(1);
    }

    System.out.println("Starting pos: " + start[0] + " " + start[1]);
    int[] prev1 = new int[]{ start[0], start[1] };
    int[] prev2 = new int[]{ start[0], start[1] };
    int[] curr1 = new int[0];
    int[] curr2 = new int[0];

    if (canMoveUp(start, start))
       curr1 = new int[]{ start[0]-1, start[1] };
    else if (canMoveRight(start, start))
       curr1 = new int[]{ start[0], start[1]+1 };
    else if (canMoveDown(start, start))
       curr1 = new int[]{ start[0]+1, start[1] };
    else
      System.out.println("Couldn't find curr1 start");

    if (canMoveLeft(start, start))
       curr2 = new int[]{ start[0], start[1]-1 };
    else if (canMoveDown(start, start))
       curr2 = new int[]{ start[0]+1, start[1] };
    else if (canMoveRight(start, start))
       curr2 = new int[]{ start[0], start[1]+1 };
    else
      System.out.println("Couldn't find curr2 start");

    grid.get(start[0])[start[1]] = getVisitedChar(grid.get(start[0])[start[1]]);
    ans1 = 1;
    while (!(curr1[0] == curr2[0] && curr1[1] == curr2[1])) {
      int[] temp1 = nextMove(curr1, prev1);
      int[] temp2 = nextMove(curr2, prev2);;

      prev1 = curr1;
      curr1 = temp1;

      prev2 = curr2;
      curr2 = temp2;
      grid.get(prev1[0])[prev1[1]] = getVisitedChar(grid.get(prev1[0])[prev1[1]]);
      grid.get(prev2[0])[prev2[1]] = getVisitedChar(grid.get(prev2[0])[prev2[1]]);

      ans1++;
    }
    grid.get(curr1[0])[curr1[1]] = getVisitedChar(grid.get(curr1[0])[curr1[1]]);
    grid.get(curr2[0])[curr2[1]] = getVisitedChar(grid.get(curr2[0])[curr2[1]]);

    StringBuilder sb = new StringBuilder();
    for (char[] row : grid) {
      for (char c : row)
        sb.append(c);
      sb.append('\n');
    }
    System.out.println(sb.toString());

    for (char[] row : grid) {
      int walls = 0;
      // L----J.
      int i=0;
      while (i<row.length) {
        if (row[i] == 'T' || row[i] == 'C') {
          walls++;
          do {
            i++;
          } while (i<row.length && row[i] != 'C');
          i++;
        } else {
          while (i<row.length && row[i] != 'C' && row[i] != 'T') {
            if (walls % 2 == 0)
              ans2++;
            i++;
          }
        }
      }
    }
  }

  private int[] nextMove(int[] curr, int[] prev) {
    int[] next = new int[0];
    if (canMoveUp(curr, prev))
        next = new int[]{ curr[0]-1, curr[1] };
    else if (canMoveRight(curr, prev))
        next = new int[]{ curr[0], curr[1]+1 };
    else if (canMoveDown(curr, prev))
        next = new int[]{ curr[0]+1, curr[1] };
    else if (canMoveLeft(curr, prev))
        next = new int[]{ curr[0], curr[1]-1 };
    else
      System.out.println("Couldn't find next move");
    return next;
  }

  private char getVisitedChar(char target) {
    char replacement;
    switch (target) {
      case 'F':
      case '7':
      case 'J':
      case 'L':
        replacement = 'C';
        break;
      default:
        replacement = 'T';
        break;
    }
    return replacement;
  }

  private boolean canMoveUp(int[] coords, int[] prev) {
    return
      coords[0] > 0 &&
      coords[0]-1 != prev[0] &&
      (legend.get(grid.get(coords[0])[coords[1]]).contains("N") ||
        (legend.get(grid.get(coords[0])[coords[1]]).contains("?") &&
        legend.get(grid.get(coords[0]-1)[coords[1]]).contains("S")));
  }

  private boolean canMoveRight(int[] coords, int[] prev) {
    return
      coords[1] < grid.get(0).length-1 &&
      coords[1]+1 != prev[1] &&
      (legend.get(grid.get(coords[0])[coords[1]]).contains("E") ||
        (legend.get(grid.get(coords[0])[coords[1]]).contains("?") &&
        legend.get(grid.get(coords[0])[coords[1]+1]).contains("W")));
  }

  private boolean canMoveDown(int[] coords, int[] prev) {
    return
      coords[0] < grid.size()-1 &&
      coords[0]+1 != prev[0] &&
      (legend.get(grid.get(coords[0])[coords[1]]).contains("S") ||
        (legend.get(grid.get(coords[0])[coords[1]]).contains("?") &&
        legend.get(grid.get(coords[0]+1)[coords[1]]).contains("N")));
  }

  private boolean canMoveLeft(int[] coords, int[] prev) {
    return
      coords[1] > 0 &&
      coords[1]-1 != prev[1] && 
      (legend.get(grid.get(coords[0])[coords[1]]).contains("W") ||
        (legend.get(grid.get(coords[0])[coords[1]]).contains("?") &&
        legend.get(grid.get(coords[0])[coords[1]-1]).contains("E")));
  }

  private int p1(List<List<Character>> grid, int[] prev, int[] curr) {
    
    return 0;
  }
  
  public static void main(String[] args) {

    // Example solution to test against
    final int solution1 = 80;
    final int solution2 = 10;

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
