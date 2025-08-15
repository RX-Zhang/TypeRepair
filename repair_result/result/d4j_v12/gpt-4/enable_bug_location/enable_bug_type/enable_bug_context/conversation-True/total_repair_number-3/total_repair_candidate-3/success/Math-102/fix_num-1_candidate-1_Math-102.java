public double chiSquare(double[] expected, long[] observed)
    throws IllegalArgumentException {
    if ((expected.length < 2) || (expected.length != observed.length)) {
        throw new IllegalArgumentException(
                "observed, expected array lengths incorrect");
    }
    if (!isPositive(expected) || !isNonNegative(observed)) {
        throw new IllegalArgumentException(
            "observed counts must be non-negative and expected counts must be positive");
    }
    double sumSq = 0.0d;
    double dev = 0.0d;
    double totalObserved = 0.0;
    double totalExpected = 0.0;

    for (int i = 0; i < observed.length; i++) {
        totalObserved += observed[i];
        totalExpected += expected[i];
    }

    for (int i = 0; i < observed.length; i++) {
        double expectedCount = (expected[i] / totalExpected) * totalObserved;
        dev = observed[i] - expectedCount;
        sumSq += (dev * dev) / expectedCount;
    }
    return sumSq;
}
