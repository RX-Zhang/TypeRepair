protected void iterateSimplex(final Comparator<RealPointValuePair> comparator)
    throws FunctionEvaluationException, OptimizationException, IllegalArgumentException {

    int iterations = 0; // Track iterations
    int maxIterations = 100; // Set a maximum number of iterations

    while (iterations < maxIterations) {
        incrementIterationsCounter();
        iterations++; // Increment the iteration count

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
                simplex = original; // Update simplex with the reflected simplex
            } else {
                simplex = reflected; // Update simplex with the reflected simplex
            }

            return;

        }

        // compute the contracted simplex
        final RealPointValuePair contracted = evaluateNewSimplex(original, gamma, comparator);
        if (comparator.compare(contracted, best) < 0) {
            // accept the contracted simplex
            simplex = contracted; // Update simplex with the contracted simplex

            // check convergence
            return;
        }

    }

    throw new OptimizationException("Maximal number of iterations exceeded"); // Throw exception if max iterations reached
}
