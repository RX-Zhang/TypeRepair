public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();
    try {
        return fit(guess);
    } catch (org.apache.commons.math.exception.NotStrictlyPositiveException e) {
        // Handle the exception gracefully, e.g., return NaN or default values
        double[] nanResult = new double[3];
        for (int i = 0; i < nanResult.length; i++) {
            nanResult[i] = Double.NaN;
        }
        return nanResult;
    }
}
