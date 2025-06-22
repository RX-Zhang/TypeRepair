public double getNumericalMean() {
    if (getPopulationSize() <= 0) {
        throw new IllegalArgumentException("Population size must be greater than zero.");
    }
    return (double) (getSampleSize() * getNumberOfSuccesses()) / (double) getPopulationSize();
}
