public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();
    // Validate or sanitize the guess values before fitting
    for (int i = 0; i < guess.length; i++) {
        if (guess[i] <= 0) {
            // Replace non-positive initial guesses with a small positive number to avoid exceptions
            guess[i] = 1e-10;
        }
    }
    return fit(guess);
}
