protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    double ret;
    if (d > 2.0) {
        // use mean only if denominatorDegreesOfFreedom > 2
        ret = d / (d - 2.0);
    } else {
        // fallback initial guess when mean is not defined
        ret = 1.0;
    }
    return ret;
}
