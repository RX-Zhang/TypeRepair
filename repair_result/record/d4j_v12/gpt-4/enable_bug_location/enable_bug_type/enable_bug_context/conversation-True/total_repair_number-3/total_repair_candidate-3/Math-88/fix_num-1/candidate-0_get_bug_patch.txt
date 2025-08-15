protected RealPointValuePair getSolution() {
    double[] coefficients = new double[getOriginalNumDecisionVariables()];
    for (int i = 0; i < coefficients.length; i++) {
        Integer basicRow = getBasicRow(getNumObjectiveFunctions() + i);
        coefficients[i] = (basicRow == null ? 0.0 : getEntry(basicRow, getRhsOffset()));
    }
    if (!restrictToNonNegative) {
        Integer basicRow = getBasicRow(getNumObjectiveFunctions() + getOriginalNumDecisionVariables());
        double mostNegative = basicRow == null ? 0 : getEntry(basicRow, getRhsOffset());
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] -= mostNegative;
        }
    }
    return new RealPointValuePair(coefficients, f.getValue(coefficients));
}
