public double cumulativeProbability(double x) throws MathException {
    if (standardDeviation <= 0) {
        throw new IllegalArgumentException("Standard deviation must be positive.");
    }
    double z = (x - mean) / (standardDeviation * Math.sqrt(2.0));
    // For very large or small z, erf may fail to converge, so handle tail cases directly
    if (z < -40) {
        return 0.0;
    } else if (z > 40) {
        return 1.0;
    }
    return 0.5 * (1.0 + Erf.erf(z));
}
