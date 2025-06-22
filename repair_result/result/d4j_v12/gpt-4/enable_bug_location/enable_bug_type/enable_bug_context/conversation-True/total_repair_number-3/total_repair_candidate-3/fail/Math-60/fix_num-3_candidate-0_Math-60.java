public double cumulativeProbability(double x) throws MathException {
    final double dev = x - getMean();
    final double sd = getStandardDeviation();
    try {
        return 0.5 * (1.0 + Erf.erf(dev / (sd * FastMath.sqrt(2.0))));
    } catch (MaxIterationsExceededException ex) {
        if (x < (getMean() - 20 * sd)) { // JDK 1.5 blows at 38
            return 0;
        } else if (x > (getMean() + 20 * sd)) {
            return 1;
        } else {
            throw ex;
        }
    }
}
