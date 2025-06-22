public double getNumericalMean() {
    return (double) (getNumberOfSuccesses() * getSampleSize()) / (double) getPopulationSize();
}
