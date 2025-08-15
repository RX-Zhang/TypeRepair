protected RealPointValuePair getSolution() {
    double[] coefficients = new double[getOriginalNumDecisionVariables()];
    Integer basicRow;
    for (int i = 0; i < coefficients.length; i++) {
        basicRow = getBasicRow(getNumObjectiveFunctions() + i);
        coefficients[i] = basicRow == null ? 0 : getEntry(basicRow, getRhsOffset());
    }
    return new RealPointValuePair(coefficients, f.getValue(coefficients));
}
