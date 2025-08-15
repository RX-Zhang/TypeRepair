protected RealPointValuePair getSolution() {
    double[] coefficients = new double[getOriginalNumDecisionVariables()];
    Integer basicRow =
        getBasicRow(getNumObjectiveFunctions() + getOriginalNumDecisionVariables());
    double mostNegative = basicRow == null ? 0 : getEntry(basicRow, getRhsOffset());
    for (int i = 0; i < coefficients.length; i++) {
        basicRow = getBasicRow(getNumObjectiveFunctions() + i);
            // if multiple variables can take a given value 
            // then we choose the first and set the rest equal to 0
            coefficients[i] =
                (basicRow == null ? 0 : getEntry(basicRow, getRhsOffset())) -
                (restrictToNonNegative ? Math.max(0, mostNegative) : mostNegative);
        if (basicRow != null) {
            for (int j = getNumObjectiveFunctions(); j < getNumObjectiveFunctions() + getOriginalNumDecisionVariables(); j++) {
                if (MathUtils.equals(tableau.getEntry(basicRow, j), 1.0, epsilon)) {
                     coefficients[i] = 0;
                }
            }
        }
    }
    return new RealPointValuePair(coefficients, f.getValue(coefficients));
}
