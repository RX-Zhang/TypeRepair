public static double distance(int[] p1, int[] p2) {
  int sum = 0;
  for (int i = 0; i < p1.length; i++) {
      final int dp = p1[i] - p2[i];
      sum += dp * dp;
  }
  return Math.sqrt(sum);
}

public static boolean equals(int[] x, int[] y) {
    if (x == y) {
        return true;
    }
    if (x == null || y == null || x.length != y.length) {
        return false;
    }
    for (int i = 0; i < x.length; i++) {
        if (x[i] != y[i]) {
            return false;
        }
    }
    return true;
}
