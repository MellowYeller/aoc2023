import java.io.*;
public class Day2 {
  public static void main(String[] args) {
    System.out.println("Day 2!");

    // Red, green, blue
    int[] maxCounts = { 12, 13, 14 };
    int sum = 0;
    int powerSum = 0;

    try (BufferedReader in = new BufferedReader(new FileReader("input2.1.txt"))) {
      String line = in.readLine();
      int i = 1;
      while (line != null && line.length() > 0) {
        int[] gameCounts = parseGame(line);
        if (gameCounts[0] <= maxCounts[0] && gameCounts[1] <= maxCounts[1] && gameCounts[2] <= maxCounts[2]) {
          sum += i;
        }
        powerSum += gameCounts[0] * gameCounts[1] * gameCounts[2];
        line = in.readLine();
        i++;
      }
    } catch (Exception e) {
      System.err.println(e);
    }

    System.out.println(sum);
    System.out.println(powerSum);
  }

  // Return the max number of red, green, and blue cubes found in a game
  private static int[] parseGame(String s) {
    String[] rounds = s.split(":")[1].split(";");
    int[] maxCounts = new int[3];
    for (String round : rounds) {
      String[] colors = round.split(",");
      for (String color : colors) {
        String[] item = color.trim().split(" ");
        int colorIndex;
        switch (item[1]) {
          case "red":
            colorIndex = 0;
            break;
          case "green":
            colorIndex = 1;
            break;
          case "blue":
            colorIndex = 2;
            break;
          default:
            colorIndex = -1;
            break;
        }
        maxCounts[colorIndex] = Math.max(Integer.parseInt(item[0]), maxCounts[colorIndex]);
      }
    }
    return maxCounts;
  }

  
}
