public double solve(double min, double max) throws MaxIterationsExceededException, 
    FunctionEvaluationException {
    
    clearResult();
    verifyInterval(min, max);
    
    double yMin = f.value(min);
    if (Math.abs(yMin) <= functionValueAccuracy) {
        setResult(min, 0);
        return result;
    }
    
    double yMax = f.value(max);
    if (Math.abs(yMax) <= functionValueAccuracy) {
        setResult(max, 0);
        return result;
    }
    
    // if min and max bracket the root, solve normally
    if (yMin * yMax < 0) {
        return solve(min, yMin, max, yMax, min, yMin);
    }
    
    // endpoints do not bracket root and are not roots themselves
    throw new IllegalArgumentException
        ("Function values at endpoints do not have different signs." +
         "  Endpoints: [" + min + "," + max + "]" + 
         "  Values: [" + yMin + "," + yMax + "]");
}
