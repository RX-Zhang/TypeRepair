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
    double totalExpected = 0.0;
    double totalObserved = 0.0;
    for (int i = 0; i < observed.length; i++) {
        totalExpected += expected[i];
        totalObserved += observed[i];
    }
    // Rescale expected counts to match total observed counts
    double scale = totalObserved / totalExpected;
    for (int i = 0; i < observed.length; i++) {
        double scaledExpected = expected[i] * scale;
        // Check again for positive scaled expected to avoid division by zero
        if (scaledExpected <= 0) {
            throw new IllegalArgumentException(
                "expected counts must all be positive after scaling");
        }
        dev = ((double) observed[i] - scaledExpected);
        sumSq += dev * dev / scaledExpected;
    }
    return sumSq;
}
