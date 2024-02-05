import java.io.*;
import java.util.*;

public class Solution {
  public String input;
  public long ans1 = 0;
  public long ans2 = 0;
  private boolean showInput = false;
  private List<Long> seeds;
  private FarmMap seedToSoil;
  private FarmMap soilToFertilizer;
  private FarmMap fertilizerToWater;
  private FarmMap waterToLight;
  private FarmMap lightToTemperature;
  private FarmMap temperatureToHumidity;
  private FarmMap humidityToLocation;

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

      // Get seeds
      this.seeds = new ArrayList<Long>();
      for (String s : line.split(":")[1].trim().split("\\s+"))
        seeds.add(Long.parseLong(s));

      do {
        line = in.readLine();
      } while (line.equals(""));

      // Get mappings
      this.seedToSoil = getMap(in);
      line = in.readLine();

      this. soilToFertilizer = getMap(in);
      line = in.readLine();

      this. fertilizerToWater = getMap(in);
      line = in.readLine();

      this.waterToLight = getMap(in);
      line = in.readLine();

      this.lightToTemperature = getMap(in);
      line = in.readLine();

      this.temperatureToHumidity = getMap(in);
      line = in.readLine();

      this.humidityToLocation = getMap(in);
    } catch (Exception e) {
      System.err.println("Encountered an error!");
      System.err.println(e);
      System.exit(1);
    }

    ans1 = Long.MAX_VALUE;
    for (Long seed : this.seeds) {
      ans1 = Math.min(ans1, mapSeed(seed));
    }

    long[][] seedRanges = new long[this.seeds.size()/2][];
    for (int i=0; i<this.seeds.size(); i+=2) {
      seedRanges[i/2] = new long[]{this.seeds.get(i), this.seeds.get(i+1)};
    }
    Arrays.sort(seedRanges, (long[] a, long[] b) -> {
      long res = a[0] - b[0];
      if (res < 0)
        return -1;
      if (res > 0)
        return 1;
      else
        return 0;
    });

    long seed = Long.MIN_VALUE;

    ans2 = Long.MAX_VALUE;
    for (long[] range : seedRanges) {
      seed = Math.max(seed, range[0]);
      System.out.println("Range " + range[0] + " - " + (range[0] + range[1]));
      while (seed < range[0] + range[1])
        ans2 = Math.min(ans2, mapSeed(seed++));
    }

    /*
    long start = Long.MAX_VALUE;
    long end = Long.MIN_VALUE;
    for (int i=0; i<this.seeds.size(); i+=2) {
      start = Math.min(start, this.seeds.get(i));
      end = Math.max(end, this.seeds.get(i) + this.seeds.get(i+1));
    }

    for (long i=start; i<end; i++) {
      if (i % 100000 == 0)
        System.out.println(i);
      if (seedInRange(i))
        ans2 = Math.min(ans2, mapSeed(i));
    }
    */
    // for (int i=0; i<this.seeds.size(); i+=2) {
    //   for (long seed = this.seeds.get(i); seed < this.seeds.get(i) + this.seeds.get(i+1); seed++) {
    //     ans2 = Math.min(ans2, mapSeed(seed));
    //   }
    // }

  }

  private boolean seedInRange(long seed) {
    for (int i=0; i<this.seeds.size(); i+=2) {
      if (seed >= this.seeds.get(i) && seed < this.seeds.get(i) + this.seeds.get(i+1))
        return true;
    }
    return false;
  }
 
  private FarmMap getMap(BufferedReader in) throws IOException {
    FarmMap map = new FarmMap();
    String line = in.readLine();
    while (line != null && !line.equals("")) {
      long[] mapping = new long[3];
      String[] nums = line.split(" +");
      for (int i=0; i<3; i++)
        mapping[i] = Long.parseLong(nums[i]);
      map.addMap(mapping);
      line = in.readLine();
    }
    return map;
  }

  private long mapSeed(long seed) {
    long soil = this.seedToSoil.get(seed);
    long fertilizer = this.soilToFertilizer.get(soil);
    long water = this.fertilizerToWater.get(fertilizer);
    long light = this.waterToLight.get(water);
    long temperature = this.lightToTemperature.get(light);
    long humidity = this.temperatureToHumidity.get(temperature);
    return this.humidityToLocation.get(humidity);
  }
 
  public static void main(String[] args) {

    // Example solution to test against
    final int solution1 = 35;
    final int solution2 = 46;

    System.out.println("Testing example...");
    Solution example = new Solution("example.txt", true);

    example.solve();

    System.out.println("Part 1:");
    System.out.println("Expected: " + solution1);
    System.out.println("Found: " + example.ans1);
    System.out.println("Part 1 passed: " + ((example.ans1 == solution1) ? "true" : "false"));
    System.out.println();

    System.out.println("Part 2:");
    System.out.println("Expected: " + solution2);
    System.out.println("Found: " + example.ans2);
    System.out.println("Part 2 passed: " + ((example.ans2 == solution2) ? "true" : "false"));
    System.out.println();

    Solution solution = new Solution("input.txt");
    if (args.length > 0 && (args[0].equals("1") || args[0].equals("2"))) {

      System.out.println("Test complete. Running for real...");

      solution.solve();

      System.out.println("Part 1:");
      System.out.println(solution.ans1);
      System.out.println();
    }

    if (args.length > 0 && args[0].equals("2")) {
      System.out.println("Part 2:");
      System.out.println(solution.ans2);
      System.out.println();
    }

  }
  
}

class FarmMap {
  private List <long[]> mappings;

  public FarmMap() {
    this.mappings = new ArrayList<long[]>();
  }

  public void addMap(long[] map) {
    this.mappings.add(map);
  }

  public long get(long n) {
    for (long[] map : this.mappings) {
      if (n >= map[1] && n < map[1] + map[2])
        return n - map[1] + map[0];
    }
    return n;
  }

}
