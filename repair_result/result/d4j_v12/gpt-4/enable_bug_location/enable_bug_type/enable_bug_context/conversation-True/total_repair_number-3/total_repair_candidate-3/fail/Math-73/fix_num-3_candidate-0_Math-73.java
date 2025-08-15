public double solve(final UnivariateRealFunction f,
                    final double min, final double max, final double initial)
    throws MaxIterationsExceededException, FunctionEvaluationException {

    clearResult();
    verifySequence(min, max);
    if (min >= max) {
        throw new IllegalArgumentException("Endpoints do not specify an interval: [" + min + ", " + max + "]");
    }

    double yMin = f.value(min);
    double yMax = f.value(max);

    // Check if the interval brackets a root
    if (yMin * yMax > 0) {
        throw new IllegalArgumentException(NON_BRACKETING_MESSAGE +
            " Endpoints: [" + min + ", " + max + "], Values: [" + yMin + ", " + yMax + "]");
    }

    // return the initial guess if it is good enough and within range
    if (initial <= min || initial >= max) {
        throw new IllegalArgumentException("Initial guess must be strictly inside the interval");
    }
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

    // return the second endpoint if it is good enough
    if (Math.abs(yMax) <= functionValueAccuracy) {
        setResult(max, 0);
        return result;
    }

    // Reduce interval if initial and min bracket the root
    if (yInitial * yMin < 0) {
        return solve(f, min, yMin, initial, yInitial, min, yMin);
    }

    // Reduce interval if initial and max bracket the root
    if (yInitial * yMax < 0) {
        return solve(f, initial, yInitial, max, yMax, initial, yInitial);
    }

    // fallback to full Brent algorithm starting with provided initial guess
    return solve(f, min, yMin, max, yMax, initial, yInitial);
}
