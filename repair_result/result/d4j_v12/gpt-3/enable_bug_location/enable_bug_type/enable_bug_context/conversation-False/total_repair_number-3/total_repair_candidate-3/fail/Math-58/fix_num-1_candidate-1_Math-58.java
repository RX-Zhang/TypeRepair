public double[] fit() {
    final double[] guess;
    try {
        guess = (new ParameterGuesser(getObservations())).guess();
    } catch (Exception e) {
        // Fallback to some default initial guess to avoid execution errors
        // Assuming the Gaussian.Parametric() expects 3 parameters: norm, mean, sigma
        guess = new double[] {1.0, 0.0, 1.0};
    }
    // Add boundary check to ensure sigma (guess[2]) is strictly positive
    if (guess.length > 2 && guess[2] <= 0) {
        guess[2] = 1e-3; // small positive number to avoid NotStrictlyPositiveException
    }
    return fit(new Gaussian.Parametric(), guess);
}
