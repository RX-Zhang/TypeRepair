protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    double ret;
    if (d > 2.0) {
        // use mean
        ret = d / (d - 2.0);
    } else {
        // mean undefined, use a small positive number as initial guess
        ret = 0.5;
    }
    return ret;
}
