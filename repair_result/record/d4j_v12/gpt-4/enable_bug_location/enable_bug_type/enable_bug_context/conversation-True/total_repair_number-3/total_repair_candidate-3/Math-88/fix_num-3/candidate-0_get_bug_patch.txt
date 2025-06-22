protected RealPointValuePair getSolution() {
    double[] coefficients = new double[getOriginalNumDecisionVariables()];
    for (int i = 0; i < coefficients.length; i++) {
        Integer basicRow = getBasicRow(getNumObjectiveFunctions() + i);
        coefficients[i] = (basicRow == null ? 0 : getEntry(basicRow, getRhsOffset()));
        if (basicRow != null) {
            for (int j = getNumObjectiveFunctions(); j < getNumObjectiveFunctions() + i; j++) {
                if (Math.abs(tableau.getEntry(basicRow, j) - 1.0) < epsilon) {
                    coefficients[i] = 0;
                    break;
                }
            }
        }
        if (!restrictToNonNegative) {
            coefficients[i] -= getEntry(0, getRhsOffset());
        }
    }
    return new RealPointValuePair(coefficients, f.getValue(coefficients));
}
