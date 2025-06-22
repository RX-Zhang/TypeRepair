protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    double ret;
    // When denominator degrees of freedom <= 2, mean is undefined (division by zero or negative)
    // Provide a fallback initial domain value in that case
    if (d > 2.0) {
        ret = d / (d - 2.0);
    } else {
        // fallback initial value for cases where mean is undefined
        ret = 1.0;
    }
    return ret;
}
