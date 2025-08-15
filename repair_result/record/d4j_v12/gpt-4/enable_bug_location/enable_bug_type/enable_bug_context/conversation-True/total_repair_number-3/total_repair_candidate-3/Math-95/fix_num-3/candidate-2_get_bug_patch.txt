protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    if (d > 2.0) {
        // use mean
        return d / (d - 2.0);
    } else {
        // use midpoint between lower and upper bound if mean is not defined
        double lower = getDomainLowerBound(p);
        double upper = getDomainUpperBound(p);
        return (lower + upper) / 2.0;
    }
}
