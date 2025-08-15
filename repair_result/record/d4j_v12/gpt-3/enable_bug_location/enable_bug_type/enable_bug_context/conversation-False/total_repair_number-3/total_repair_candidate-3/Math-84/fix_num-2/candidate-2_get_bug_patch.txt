protected void iterateSimplex(final Comparator<RealPointValuePair> comparator)
    throws FunctionEvaluationException, OptimizationException, IllegalArgumentException {

    int iterationCount = 0; // Add a counter for iterations

    while (iterationCount < maxIterations) { // Replace while(true) with a convergence check

        incrementIterationsCounter();
        iterationCount++; // Increment the iteration count

        // save the original vertex
        final RealPointValuePair[] original = simplex;
        final RealPointValuePair best = original[0];

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
            simplex = new RealPointValuePair[] { contracted }; // Update simplex with the contracted value

            // check convergence (not implemented, placeholder for actual convergence check)
            if (/* Check for convergence condition */) {
                return;
            }
        }
    }

    throw new MaxIterationsExceededException("Maximal number of iterations (" + maxIterations + ") exceeded");
}
