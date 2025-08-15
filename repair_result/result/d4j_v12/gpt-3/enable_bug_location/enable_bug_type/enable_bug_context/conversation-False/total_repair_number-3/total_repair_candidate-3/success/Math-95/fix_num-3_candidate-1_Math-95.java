protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    double ret;
    // when denominatorDegreesOfFreedom <= 2, mean is undefined (division by zero or negative)
    // choose a safe default initial value, e.g., 1.0
    if (d > 2.0) {
        ret = d / (d - 2.0);
    } else {
        ret = 1.0;
    }
    return ret;
}
