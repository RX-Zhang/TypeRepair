public static double distance(int[] p1, int[] p2) {
    if (p1 == null || p2 == null) {
        throw new IllegalArgumentException("Input arrays must not be null");
    }
    if (p1.length != p2.length) {
        throw new IllegalArgumentException("Input arrays must have the same length");
    }
    long sum = 0;
    for (int i = 0; i < p1.length; i++) {
        long dp = (long)p1[i] - (long)p2[i];
        sum += dp * dp;
        if (sum < 0) { // overflow check
            throw new ArithmeticException("Integer overflow while computing distance");
        }
    }
    return Math.sqrt(sum);
}
