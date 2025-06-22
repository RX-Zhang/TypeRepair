public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();
    double[] result;
    try {
        result = fit(new Gaussian.Parametric(), guess);
    } catch (org.apache.commons.math.exception.NotStrictlyPositiveException e) {
        // Handle exception by returning NaNs or some sentinel values
        result = new double[guess.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.NaN;
        }
    }
    return result;
}
