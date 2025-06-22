public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();
    // Validate initial guess to ensure parameters are strictly positive where required
    if (guess.length > 2) {
        // Clamp sigma (guess[2]) to a positive value to avoid NotStrictlyPositiveException in Gaussian
        if (guess[2] <= 0) {
            guess[2] = 1e-10;
        }
    }
    return fit(guess);
}
