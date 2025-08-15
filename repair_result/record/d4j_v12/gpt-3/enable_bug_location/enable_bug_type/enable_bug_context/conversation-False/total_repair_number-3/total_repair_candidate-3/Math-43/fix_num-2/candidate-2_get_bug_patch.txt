public void addValue(double value) {
    sumImpl.increment(value);
    sumsqImpl.increment(value);
    minImpl.increment(value);
    maxImpl.increment(value);
    sumLogImpl.increment(value);
    secondMoment.increment(value);
    // If mean, variance or geomean have been overridden,
    // need to increment these
    if (!(meanImpl instanceof Mean) && n > 0) {
        meanImpl.increment(value);
    }
    if (!(varianceImpl instanceof Variance) && n > 0) {
        varianceImpl.increment(value);
    }
    if (!(geoMeanImpl instanceof GeometricMean) && n > 0) {
        geoMeanImpl.increment(value);
    }
    n++;
}
