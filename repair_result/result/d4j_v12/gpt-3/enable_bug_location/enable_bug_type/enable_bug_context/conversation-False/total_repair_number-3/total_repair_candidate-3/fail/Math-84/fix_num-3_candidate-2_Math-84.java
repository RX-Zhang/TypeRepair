protected void iterateSimplex(final Comparator<RealPointValuePair> comparator)
    throws FunctionEvaluationException, OptimizationException, IllegalArgumentException {

    int iterations = 0; // Initialize the iteration count

    while (iterations < maxIterations) { // Replace while(true) with convergence check

        incrementIterationsCounter();
        iterations++; // Increment iterations

        // save the original vertex
        final RealPointValuePair[] original = simplex;
        final RealPointValuePair best = original[0];

        // perform a reflection step
        final RealPointValuePair reflected = evaluateNewSimplex(original, 1.0, comparator);
        if (comparator.compare(reflected, best) < 0) {

            // compute the expanded simplex
            final RealPointValuePair expanded = evaluateNewSimplex(original, khi, comparator);
            if (comparator.compare(reflected, expanded) <= 0) {
                // accept the reflected simplex
                simplex = new RealPointValuePair[] { reflected }; // Fix assignment to reflect the new simplex
            } else {
                simplex = original; // Make sure to revert to the original if expansion is not accepted
            }

            return;

        }

        // compute the contracted simplex
        final RealPointValuePair contracted = evaluateNewSimplex(original, gamma, comparator);
        if (comparator.compare(contracted, best) < 0) {
            // accept the contracted simplex
            simplex = new RealPointValuePair[] { contracted }; // Fix assignment to accept the contracted simplex

            return; // Check convergence after accepting the contracted simplex
        }

    }

    throw new OptimizationException("Maximal number of iterations exceeded"); // Add exception handling for exceeding max iterations
}
