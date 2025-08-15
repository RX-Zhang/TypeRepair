public static int distance1(int[] p1, int[] p2) {
  int sum = 0;
  for (int i = 0; i < p1.length; i++) {
      sum += Math.abs(p1[i] - p2[i]);
  }
  return sum;
}
