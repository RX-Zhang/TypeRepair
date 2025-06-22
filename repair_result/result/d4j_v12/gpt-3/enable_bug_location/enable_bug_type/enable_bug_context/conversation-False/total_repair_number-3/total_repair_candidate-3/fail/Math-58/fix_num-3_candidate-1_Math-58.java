public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();
    try {
        return fit(new Gaussian.Parametric(), guess);
    } catch (org.apache.commons.math.exception.NotStrictlyPositiveException e) {
        // Return NaNs if parameters are invalid (e.g., negative sigma)
        double[] nanResult = new double[guess.length];
        for (int i = 0; i < nanResult.length; i++) {
            nanResult[i] = Double.NaN;
        }
        return nanResult;
    }
}
