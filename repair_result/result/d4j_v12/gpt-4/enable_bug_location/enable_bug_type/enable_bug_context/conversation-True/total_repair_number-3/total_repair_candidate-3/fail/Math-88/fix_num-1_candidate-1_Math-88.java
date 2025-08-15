protected RealPointValuePair getSolution() {
    double[] coefficients = new double[getOriginalNumDecisionVariables()];
    for (int i = 0; i < coefficients.length; i++) {
        Integer basicRow = getBasicRow(getNumObjectiveFunctions() + i);
        coefficients[i] = basicRow == null ? 0 : getEntry(basicRow, getRhsOffset());
    }
    if (!restrictToNonNegative) {
        double minCoeff = Double.POSITIVE_INFINITY;
        for (double coeff : coefficients) {
            if (coeff < minCoeff) {
                minCoeff = coeff;
            }
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] -= minCoeff;
        }
    }
    return new RealPointValuePair(coefficients, f.getValue(coefficients));
}
