protected void iterateSimplex(final Comparator<RealPointValuePair> comparator)
    throws FunctionEvaluationException, OptimizationException, IllegalArgumentException {

    double previousBestValue = Double.NaN;
    int iter = 0;
    final int maxIter = getMaxIterations();

    while (iter < maxIter) {

        incrementIterationsCounter();

        // save the original vertex
        final RealPointValuePair[] original = simplex;
        final RealPointValuePair best = original[0];

        // check for convergence
        if (!Double.isNaN(previousBestValue)) {
            double currentBestValue = best.getValue();
            if (Math.abs(currentBestValue - previousBestValue) <= getConvergenceChecker().getAbsoluteThreshold()) {
                return;
            }
        }
        previousBestValue = best.getValue();

        // perform a reflection step
        final RealPointValuePair reflected = evaluateNewSimplex(original, 1.0, comparator);
        if (comparator.compare(reflected, best) < 0) {

            // compute the expanded simplex
            final RealPointValuePair[] reflectedSimplex = simplex;
            final RealPointValuePair expanded = evaluateNewSimplex(original, khi, comparator);
            if (comparator.compare(reflected, expanded) <= 0) {
                // accept the reflected simplex
                simplex = reflectedSimplex;
            }

            return;

        }

        // compute the contracted simplex
        final RealPointValuePair contracted = evaluateNewSimplex(original, gamma, comparator);
        if (comparator.compare(contracted, best) < 0) {
            // accept the contracted simplex

            // check convergence
            return;
        }

        iter++;
    }

    throw new OptimizationException(new MaxIterationsExceededException(maxIter));
}
