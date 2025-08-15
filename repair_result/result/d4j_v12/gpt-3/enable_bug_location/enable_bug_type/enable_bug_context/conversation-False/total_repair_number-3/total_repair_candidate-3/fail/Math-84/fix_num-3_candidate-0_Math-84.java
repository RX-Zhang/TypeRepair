protected void iterateSimplex(final Comparator<RealPointValuePair> comparator)
    throws FunctionEvaluationException, OptimizationException, IllegalArgumentException {

    int iterations = 0; // Track the number of iterations
    final int maxIterations = 100; // Set a maximum number of iterations

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
                simplex = new RealPointValuePair[original.length];
                System.arraycopy(original, 0, simplex, 0, original.length);
                simplex[0] = reflected; // Update the first element with the reflected point
            } else {
                // If the expanded point is better, accept it
                simplex = new RealPointValuePair[original.length];
                System.arraycopy(original, 0, simplex, 0, original.length);
                simplex[0] = expanded; // Update the first element with the expanded point
            }

            return;

        }

        // compute the contracted simplex
        final RealPointValuePair contracted = evaluateNewSimplex(original, gamma, comparator);
        if (comparator.compare(contracted, best) < 0) {
            // accept the contracted simplex
            simplex = new RealPointValuePair[original.length];
            System.arraycopy(original, 0, simplex, 0, original.length);
            simplex[0] = contracted; // Update the first element with the contracted point

            // check convergence
            return;
        }

    }

    throw new OptimizationException("Maximal number of iterations (" + maxIterations + ") exceeded");
}
