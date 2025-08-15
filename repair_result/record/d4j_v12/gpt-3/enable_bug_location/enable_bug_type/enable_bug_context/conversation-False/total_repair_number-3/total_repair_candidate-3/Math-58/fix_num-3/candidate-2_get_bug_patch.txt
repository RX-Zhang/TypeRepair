public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();
    try {
        return fit(new Gaussian.Parametric(), guess);
    } catch (NotStrictlyPositiveException e) {
        // Handle exception by returning NaN for all parameters to signal failure
        double[] nanResult = new double[guess.length];
        for (int i = 0; i < nanResult.length; i++) {
            nanResult[i] = Double.NaN;
        }
        return nanResult;
    }
}
