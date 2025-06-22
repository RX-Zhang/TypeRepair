public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();
    // Ensure initial guess parameters are valid before fitting
    for (int i = 0; i < guess.length; i++) {
        if (Double.isNaN(guess[i]) || Double.isInfinite(guess[i]) || guess[i] <= 0.0) {
            guess[i] = 1.0; // default safe positive value
        }
    }
    return fit(guess);
}
