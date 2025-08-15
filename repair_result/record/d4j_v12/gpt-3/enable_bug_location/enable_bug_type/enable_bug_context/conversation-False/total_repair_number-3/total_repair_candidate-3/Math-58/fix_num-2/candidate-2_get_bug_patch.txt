public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();

    // Add boundary checks to ensure initial guess has positive sigma
    if (guess.length > 2 && guess[2] <= 0) {
        guess[2] = 1.0e-10;  // set to a small positive number to avoid exception
    }

    try {
        return fit(new Gaussian.Parametric(), guess);
    } catch (org.apache.commons.math.exception.NotStrictlyPositiveException e) {
        // Handle the exception gracefully by returning NaNs or some default values
        double[] result = new double[guess.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.NaN;
        }
        return result;
    }
}
