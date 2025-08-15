public double[] fit() {
    final double[] guess = (new ParameterGuesser(getObservations())).guess();
    try {
        return fit(new Gaussian.Parametric(), guess);
    } catch (NotStrictlyPositiveException e) {
        // Handle exception by returning a fallback or indicating failure
        // For example, return an array of NaNs with the same length as guess
        double[] nanResult = new double[guess.length];
        for (int i = 0; i < nanResult.length; i++) {
            nanResult[i] = Double.NaN;
        }
        return nanResult;
    }
}
