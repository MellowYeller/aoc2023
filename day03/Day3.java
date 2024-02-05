import java.io.*;
import java.util.*;

public class Day3 {
  public static void main(String[] args) {

    List<char[]> list = new ArrayList<>();
    try (BufferedReader in = new BufferedReader(new FileReader("input.txt"))) {
      String line = in.readLine();
      while (line != null && line.length() > 0) {
        list.add(line.toCharArray());
        line = in.readLine();
      }
    } catch (Exception e) {
      System.err.println(e);
    }
    char[][] a = new char[0][0];
    a = list.toArray(a);

    StringBuilder sb = new StringBuilder();
    for (int row=0; row<a.length; row++) {
      for (int col=0; col<a[row].length; col++) {
        sb.append(a[row][col]);
      }
      sb.append('\n');
    }
    // System.out.println(sb.toString());

    System.out.println(sumPartNumbers(a));
    System.out.println(sumGearRatios(a));
  }

  private static int sumPartNumbers(char[][] a) {
    int sum = 0;

    for (int row=0; row<a.length; row++) {
      for (int col=0; col<a[row].length; col++) {
        if (a[row][col] >= '0' && a[row][col] <= '9') {
          StringBuilder sb = new StringBuilder();
          int start = col;
          while (col < a[row].length && a[row][col] >= '0' && a[row][col] <= '9') {
            sb.append(a[row][col]);
            col++;
          }
          int end = col;
          // start is inclusive
          // end is EXCLUSIVE

          boolean foundSymbol = false;
          if (start-1 >= 0 && isSymbol(a[row][start-1]))
            foundSymbol = true;
          if (!foundSymbol && end < a[row].length && isSymbol(a[row][end]))
            foundSymbol = true;
          if (!foundSymbol && row - 1 >= 0) {
            for (int i=Math.max(0,start-1); i<=end && i<a[row-1].length && !foundSymbol; i++) {
              if (isSymbol(a[row-1][i])) {
                foundSymbol = true;
              }
            }
          }
          if (!foundSymbol && row + 1 < a.length) {
            for (int i=Math.max(0,start-1); i<=end && i<a[row+1].length && !foundSymbol; i++) {
              if (isSymbol(a[row+1][i])) {
                foundSymbol = true;
              }
            }
          }
          if (foundSymbol) sum += Integer.parseInt(sb.toString());
        }
      }
    }
    return sum;
  }

  private static boolean isSymbol(char c) {
    return (c < '0' || c > '9') && c != '.';
  }

  private static long sumGearRatios(char[][] a) {
    long sum = 0;
    Map <String, List<Integer>> map = new HashMap<>();

    for (int row=0; row<a.length; row++) {
      for (int col=0; col<a[row].length; col++) {
        if (a[row][col] >= '0' && a[row][col] <= '9') {
          StringBuilder sb = new StringBuilder();
          int start = col;
          while (col < a[row].length && a[row][col] >= '0' && a[row][col] <= '9') {
            sb.append(a[row][col]);
            col++;
          }
          int end = col;
          int value = Integer.parseInt(sb.toString());
          // start is inclusive
          // end is EXCLUSIVE

          if (start-1 >= 0 && a[row][start-1] == '*')
            addToMap(map, row + "," + (start-1), value);
          if (end < a[row].length && a[row][end] == '*')
            addToMap(map, row + "," + end, value);
          if (row - 1 >= 0) {
            for (int i=Math.max(0,start-1); i<=end && i<a[row-1].length; i++)
              if (a[row-1][i] == '*')
                addToMap(map, (row-1) + "," + i, value);
          }
          if (row + 1 < a.length) {
            for (int i=Math.max(0,start-1); i<=end && i<a[row+1].length; i++) {
              if (a[row+1][i] == '*') {
                addToMap(map, (row+1) + "," + i, value);
              }
            }
          }
        }
      }
    }
    for (String s : map.keySet()) {
      if (map.get(s).size() == 2) {
        int product = 1;
        for (int n : map.get(s))
          product *= n;
        sum += product;
      }
    }
    return sum;
  }

  private static void addToMap(Map<String, List<Integer>> m, String s, int v) {
    if (m.containsKey(s)) m.get(s).add(v);
    else m.put(s, new ArrayList<Integer>(Arrays.asList(v)));
  }

  
}
