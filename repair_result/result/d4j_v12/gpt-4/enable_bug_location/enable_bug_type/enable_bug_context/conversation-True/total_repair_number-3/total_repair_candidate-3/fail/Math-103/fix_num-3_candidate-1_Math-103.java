public double cumulativeProbability(double x) throws MathException {
    if (x == Double.NEGATIVE_INFINITY) {
        return 0.0;
    }
    if (x == Double.POSITIVE_INFINITY) {
        return 1.0;
    }
    return 0.5 * (1.0 + Erf.erf((x - mean) / (standardDeviation * Math.sqrt(2.0))));
}
