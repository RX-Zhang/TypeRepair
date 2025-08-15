public double cumulativeProbability(double x) throws MathException {
    if (standardDeviation <= 0) {
        throw new IllegalStateException("Standard deviation must be positive.");
    }
    double z = (x - mean) / (standardDeviation * Math.sqrt(2.0));
    // Boundary checks to improve numerical stability
    if (z < -40) {
        return 0.0;
    }
    if (z > 40) {
        return 1.0;
    }
    return 0.5 * (1.0 + Erf.erf(z));
}
