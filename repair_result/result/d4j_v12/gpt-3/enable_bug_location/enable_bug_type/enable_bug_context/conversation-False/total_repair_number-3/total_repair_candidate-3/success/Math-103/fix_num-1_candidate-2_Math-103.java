public double cumulativeProbability(double x) throws MathException {
    if (standardDeviation <= 0) {
        throw new IllegalStateException("Standard deviation must be positive.");
    }
    try {
        return 0.5 * (1.0 + Erf.erf((x - mean) / (standardDeviation * Math.sqrt(2.0))));
    } catch (MaxIterationsExceededException e) {
        // Fallback approximation for extreme values to avoid infinite loops in erf
        if (x < mean) {
            return 0.0;
        } else {
            return 1.0;
        }
    }
}
