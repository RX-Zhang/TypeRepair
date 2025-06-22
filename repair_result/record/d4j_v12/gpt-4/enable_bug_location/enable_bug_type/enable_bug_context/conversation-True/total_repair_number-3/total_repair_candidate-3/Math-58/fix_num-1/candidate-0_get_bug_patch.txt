public double[] fit() {
    double[] guess = (new ParameterGuesser(getObservations())).guess();

    // Validate that the sigma parameter (usually guess[2]) is strictly positive
    if (guess.length >= 3 && guess[2] <= 0) {
        guess[2] = 1e-10; // a small positive value to avoid NotStrictlyPositiveException
    }

    return fit(guess);
}
