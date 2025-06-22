public double solve(final UnivariateRealFunction f,
                    final double min, final double max, final double initial)
    throws MaxIterationsExceededException, FunctionEvaluationException {

    clearResult();
    verifySequence(min, max);
    
    // check endpoints bracket the root
    double yMin = f.value(min);
    double yMax = f.value(max);

    if (yMin * yMax > 0) {
        throw new IllegalArgumentException(
            NON_BRACKETING_MESSAGE + " Endpoints: [" + min + ", " + max + "], Values: [" + yMin + ", " + yMax + "]");
    }

    // return the initial guess if it is good enough
    double yInitial = f.value(initial);
    if (Math.abs(yInitial) <= functionValueAccuracy) {
        setResult(initial, 0);
        return result;
    }

    // return the first endpoint if it is good enough
    if (Math.abs(yMin) <= functionValueAccuracy) {
        setResult(min, 0);
        return result;
    }

    // reduce interval if min and initial bracket the root
    if (yInitial * yMin < 0) {
        return solve(f, min, yMin, initial, yInitial, min, yMin);
    }

    // return the second endpoint if it is good enough
    if (Math.abs(yMax) <= functionValueAccuracy) {
        setResult(max, 0);
        return result;
    }

    // reduce interval if initial and max bracket the root
    if (yInitial * yMax < 0) {
        return solve(f, initial, yInitial, max, yMax, initial, yInitial);
    }

    // if no bracket found among initial and endpoints, error
    throw new IllegalArgumentException(
        NON_BRACKETING_MESSAGE + " Endpoints: [" + min + ", " + max + "], Values: [" + yMin + ", " + yMax + "]");
}
