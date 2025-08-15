public double cumulativeProbability(double x) throws MathException {
    if (x == Double.NEGATIVE_INFINITY) {
        return 0.0;
    }
    if (x == Double.POSITIVE_INFINITY) {
        return 1.0;
    }
    double mean = getMean();
    double stdDev = getStandardDeviation();
    if (stdDev <= 0) {
        throw new MathException("Standard deviation must be positive.");
    }
    return 0.5 * (1.0 + Erf.erf((x - mean) / (stdDev * Math.sqrt(2.0))));
}
