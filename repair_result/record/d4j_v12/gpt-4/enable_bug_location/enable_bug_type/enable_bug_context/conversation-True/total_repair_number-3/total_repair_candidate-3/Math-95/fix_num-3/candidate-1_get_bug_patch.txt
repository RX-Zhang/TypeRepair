protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    if (d > 2.0) {
        // use mean
        return d / (d - 2.0);
    } else {
        // default initial domain when mean is not defined
        return 1.0;
    }
}
