protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    // use mean if denominator degrees of freedom > 2, else use a fallback value such as 1.0
    if (d > 2.0) {
        return d / (d - 2.0);
    } else {
        return 1.0;
    }
}
