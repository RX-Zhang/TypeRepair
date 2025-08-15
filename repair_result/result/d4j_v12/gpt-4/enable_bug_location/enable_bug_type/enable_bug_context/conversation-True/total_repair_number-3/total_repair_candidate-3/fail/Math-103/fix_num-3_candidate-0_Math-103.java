public double cumulativeProbability(double x) throws MathException {
    if (Double.isNaN(x)) {
        return Double.NaN;
    }
    double dev = x - mean;
    if (standardDeviation < 0) {
        throw new MathException("Standard deviation must be non-negative");
    }
    if (standardDeviation == 0) {
        return x < mean ? 0d : 1d;
    }
    return 0.5 * (1.0 + Erf.erf(dev / (standardDeviation * Math.sqrt(2.0))));
}
