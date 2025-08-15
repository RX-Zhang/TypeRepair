protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    double ret;
    if (d > 2.0) {
        // use mean if defined
        ret = d / (d - 2.0);
    } else {
        // use midpoint of domain otherwise
        ret = 0.5 * (getDomainLowerBound(p) + getDomainUpperBound(p));
    }
    return ret;
}
