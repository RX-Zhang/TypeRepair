public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();

    // Sanitize and validate the initial guess to ensure parameters are valid for Gaussian.Parametric
    // In particular, sigma (guess[2]) must be strictly positive
    if (guess.length < 3 || guess[2] <= 0) {
        // Provide a fallback positive sigma if invalid
        guess[2] = 1.0;
    }

    return fit(guess);
}
