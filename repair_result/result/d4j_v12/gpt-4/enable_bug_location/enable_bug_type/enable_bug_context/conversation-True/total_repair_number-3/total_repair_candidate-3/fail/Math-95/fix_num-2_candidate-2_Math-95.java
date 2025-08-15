protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    double ret;
    if (d > 2.0) {
        // use mean
        ret = d / (d - 2.0);
    } else {
        // fallback initial estimate if mean is undefined
        ret = (getDomainLowerBound(p) + getDomainUpperBound(p)) / 2.0;
    }
    return ret;
}
