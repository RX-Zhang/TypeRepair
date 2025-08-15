public double cumulativeProbability(double x) throws MathException {
    if (standardDeviation <= 0) {
        throw new IllegalArgumentException("Standard deviation must be positive.");
    }
    double z = (x - mean) / (standardDeviation * Math.sqrt(2.0));
    double erfResult;
    try {
        erfResult = Erf.erf(z);
    } catch (Exception e) {
        // Handle possible exceptions from Erf.erf gracefully
        throw new MathException("Error computing error function: " + e.getMessage(), e);
    }
    return 0.5 * (1.0 + erfResult);
}
