public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();

    // Validate initial guess parameters to avoid negative sigma or other invalid values.
    if (guess == null || guess.length < 3) {
        throw new IllegalArgumentException("Invalid initial guess parameters.");
    }
    // Sigma parameter: guess[2] must be strictly positive.
    if (guess[2] <= 0) {
        guess[2] = 1.0; // Set a default positive sigma if invalid.
    }

    return fit(guess);
}
