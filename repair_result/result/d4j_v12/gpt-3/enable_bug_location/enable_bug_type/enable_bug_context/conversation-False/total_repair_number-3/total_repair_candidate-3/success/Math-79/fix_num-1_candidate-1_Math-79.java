public static double distance(int[] p1, int[] p2) {
  if (p1 == null || p2 == null) {
      throw new IllegalArgumentException("Input arrays must not be null.");
  }
  if (p1.length != p2.length) {
      throw new IllegalArgumentException("Input arrays must have the same length.");
  }
  long sum = 0;
  for (int i = 0; i < p1.length; i++) {
      long dp = (long)p1[i] - (long)p2[i];
      sum += dp * dp;
      if (sum < 0) {
          // overflow detected; fallback to double accumulation
          double dSum = 0.0;
          for (int j = 0; j < p1.length; j++) {
              double diff = (double)p1[j] - (double)p2[j];
              dSum += diff * diff;
          }
          return Math.sqrt(dSum);
      }
  }
  return Math.sqrt(sum);
}
