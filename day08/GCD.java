import java.util.*;
class GCD {

  public static void main(String[] args) {
    long a = 8;
    long b = 8;
    long c = 6;

    long res = gcd(a,b,c);

    System.out.println("a = " + a);
    System.out.println("b = " + b);
    System.out.println("c = " + c);
    System.out.println("gcd(a, b, c) = " + res);
    System.out.println("lcm(a, b, c) = " + lcm(a,b,c));
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
    while (a != b) {
      if (a > b)
        a = a - b;
      else
        b = b - a;
    }
    return a;
  }
}
