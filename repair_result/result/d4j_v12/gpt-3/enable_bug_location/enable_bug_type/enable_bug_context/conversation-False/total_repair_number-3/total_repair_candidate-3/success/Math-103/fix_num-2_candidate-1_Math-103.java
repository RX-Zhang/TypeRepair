public double cumulativeProbability(double x) throws MathException {
    double diff = (x - mean);
    double denom = standardDeviation * Math.sqrt(2.0);

    if (denom == 0) {
        throw new IllegalArgumentException("Standard deviation must be positive and non-zero.");
    }

    double ratio = diff / denom;

    // Boundary checks to avoid numerical issues in Erf.erf computation that can cause excessive iterations
    if (ratio < -10) {
        return 0.0;
    } else if (ratio > 10) {
        return 1.0;
    }

    return 0.5 * (1.0 + Erf.erf(ratio));
}
