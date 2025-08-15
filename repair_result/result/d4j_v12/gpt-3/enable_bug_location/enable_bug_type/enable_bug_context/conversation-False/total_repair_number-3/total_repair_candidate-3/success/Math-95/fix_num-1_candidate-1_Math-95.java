protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    double ret;
    // The mean is defined only if denominatorDegreesOfFreedom > 2
    if (d > 2.0) {
        // use mean
        ret = d / (d - 2.0);
    } else {
        // When denominatorDegreesOfFreedom <= 2, mean is not defined
        // Choose a small positive initial guess greater than 0.0
        ret = 0.5;
    }
    return ret;
}
