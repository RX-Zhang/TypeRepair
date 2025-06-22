public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();
    try {
        return fit(guess);
    } catch (org.apache.commons.math.exception.NotStrictlyPositiveException e) {
        // Return NaN values or handle the exception gracefully
        return new double[] { Double.NaN, Double.NaN, Double.NaN };
    }
}
