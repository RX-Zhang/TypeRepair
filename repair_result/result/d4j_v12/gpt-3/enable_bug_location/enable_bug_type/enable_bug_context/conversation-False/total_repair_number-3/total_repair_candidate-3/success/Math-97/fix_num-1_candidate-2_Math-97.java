public double solve(double min, double max) throws MaxIterationsExceededException, 
    FunctionEvaluationException {
    
    clearResult();
    
    // Input validation for the interval
    if (min >= max) {
        throw new IllegalArgumentException("Invalid interval. Min must be less than max." +
                "  Interval: [" + min + "," + max + "]");
    }
    
    double ret = Double.NaN;
    
    double yMin = f.value(min);
    double yMax = f.value(max);
    
    // Verify bracketing
    double sign = yMin * yMax;
    if (sign >= 0) {
        // check if either value is close to zero
        if (Math.abs(yMin) <= functionValueAccuracy || Math.abs(yMax) <= functionValueAccuracy) {
            // at least one endpoint is a root
            ret = (Math.abs(yMin) <= functionValueAccuracy) ? min : max;
            setResult(ret, 0);
            return ret;
        }
        // neither value is close to zero and min and max do not bracket root.
        throw new IllegalArgumentException
        ("Function values at endpoints do not have different signs." +
                "  Endpoints: [" + min + "," + max + "]" + 
                "  Values: [" + yMin + "," + yMax + "]");
    } else {
        // solve using only the first endpoint as initial guess
        ret = solve(min, yMin, max, yMax, min, yMin);
        // either min or max is a root
    }

    return ret;
}
