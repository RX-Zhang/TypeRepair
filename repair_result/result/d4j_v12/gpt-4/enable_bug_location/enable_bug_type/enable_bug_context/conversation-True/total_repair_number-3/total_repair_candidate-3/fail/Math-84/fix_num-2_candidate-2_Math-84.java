protected void iterateSimplex(final Comparator<RealPointValuePair> comparator)
    throws FunctionEvaluationException, OptimizationException, IllegalArgumentException {

    while (true) {

        incrementIterationsCounter();

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
            simplex = simplex; // Accept contracted simplex by assigning current simplex

            // check convergence
            return;
        }

        // If neither reflection nor contraction improved the simplex,
        // perform a shrink step to try to improve
        final RealPointValuePair[] shrunkenSimplex = new RealPointValuePair[simplex.length];
        final double[] xBest = simplex[0].getPointRef();
        shrunkenSimplex[0] = simplex[0];
        for (int i = 1; i < simplex.length; ++i) {
            final double[] x = simplex[i].getPointRef();
            final double[] xNew = new double[x.length];
            for (int j = 0; j < x.length; ++j) {
                xNew[j] = xBest[j] + gamma * (x[j] - xBest[j]);
            }
            shrunkenSimplex[i] = new RealPointValuePair(xNew, Double.NaN, false);
        }
        simplex = shrunkenSimplex;

        // Evaluate the shrunken simplex
        evaluateSimplex(comparator);

        // check convergence
        return;

    }

}
