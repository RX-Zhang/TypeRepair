protected double getInitialDomain(double p) {
    double ret;
    double d = getDenominatorDegreesOfFreedom();
    // if denominator degrees of freedom is less or equal to 2, 
    // mean is not defined, so choose some safe initial value
    if (d > 2.0) {
        ret = d / (d - 2.0);
    } else {
        // fallback initial domain value, for example 1.0
        ret = 1.0;
    }
    return ret;
}
