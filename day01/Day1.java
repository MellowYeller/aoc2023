import java.io.*;
public class Day1 {
  public static void main(String[] args) {
    System.out.println("Day 1!");
    

    try (BufferedReader in = new BufferedReader(new FileReader("input1.1.txt"))) {
      String line = in.readLine();
      int total = 0;

      do {
        total += getCalibrationValue2(line);
        line = in.readLine();
      } while (line != null && line.length() > 0);
      System.out.println(total);
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  private static int getCalibrationValue(String s) {
    int a = 0;
    int b = 0;

    for (int i=0; i<s.length() && a == 0; i++) {
      if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
        a = s.charAt(i) - '0';
    }

    for (int i=s.length()-1; i>=0 && b == 0; i--) {
      if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
        b = s.charAt(i) - '0';
    }

    return a * 10 + b;
  }

  private static int getCalibrationValue2(String s) {
    int a = 0;
    int b = 0;

    for (int i=0; i<s.length() && a == 0; i++) {
      a = matchValForward(s, i);
    }

    for (int i=s.length()-1; i>=0 && b == 0; i--)
      b = matchValForward(s, i);

    return a * 10 + b;
  }

  private static final String[] words = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
  private static int matchValForward(String s, int startIndex) {
    if (s.charAt(startIndex) >= '0' && s.charAt(startIndex) <= '9')
	    return s.charAt(startIndex) - '0';
    
    for (int wordIndex=0; wordIndex<words.length; wordIndex++) {
      int i=0;
      while (i < words[wordIndex].length() && startIndex + i < s.length()) {
        if (s.charAt(startIndex + i) == words[wordIndex].charAt(i)) i++;
        else break;
      }
      if (i == words[wordIndex].length()) return wordIndex+1;
    }
    return 0;

  }
}
