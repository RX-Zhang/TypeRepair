protected double getInitialDomain(double p) {
    double d = getDenominatorDegreesOfFreedom();
    // If degrees of freedom is less than or equal to 2, mean is undefined.
    // Return a reasonable positive initial guess instead of mean.
    if (d > 2.0) {
        return d / (d - 2.0);
    } else {
        // Return 1.0 as a safe default initial domain value when mean is undefined.
        return 1.0;
    }
}
