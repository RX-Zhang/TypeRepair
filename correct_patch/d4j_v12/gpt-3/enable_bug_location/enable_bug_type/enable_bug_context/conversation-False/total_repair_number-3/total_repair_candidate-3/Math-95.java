protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    double ret;
    // mean only defined if denominatorDegreesOfFreedom > 2
    if (d > 2.0) {
        ret = d / (d - 2.0);
    } else {
        // if mean undefined, return a positive value within the domain
        ret = 1.0;
    }
    return ret;
}
