protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    double ret;
    if (d > 2.0) {
        // use mean when denominator degrees of freedom > 2
        ret = d / (d - 2.0);
    } else {
        // fallback initial domain value when mean is not defined
        ret = 1.0;
    }
    return ret;
}
