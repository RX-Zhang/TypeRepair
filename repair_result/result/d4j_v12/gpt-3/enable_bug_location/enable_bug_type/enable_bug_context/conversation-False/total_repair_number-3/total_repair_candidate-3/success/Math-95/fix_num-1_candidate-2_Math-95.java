protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    // If denominator degrees of freedom <= 2, mean is undefined; return a positive default instead
    if (d <= 2.0) {
        return 1.0;
    }
    // use mean
    return d / (d - 2.0);
}
