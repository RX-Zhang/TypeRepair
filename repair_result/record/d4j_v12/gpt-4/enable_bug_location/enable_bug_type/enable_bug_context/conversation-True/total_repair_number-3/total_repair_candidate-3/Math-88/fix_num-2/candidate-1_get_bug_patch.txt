protected RealPointValuePair getSolution() {
    int originalNumDecisionVariables = getOriginalNumDecisionVariables();
    double[] coefficients = new double[originalNumDecisionVariables];
    Integer basicRow;
    // Find most negative value for shift if not restricted to non-negative
    Integer mostNegativeRow = getBasicRow(getNumObjectiveFunctions() + originalNumDecisionVariables);
    double mostNegative = (mostNegativeRow == null ? 0 : getEntry(mostNegativeRow, getRhsOffset()));
    for (int i = 0; i < originalNumDecisionVariables; i++) {
        basicRow = getBasicRow(getNumObjectiveFunctions() + i);
        // If multiple variables can take a given value 
        // then we choose the first and set the rest equal to 0
        coefficients[i] = (basicRow == null ? 0 : getEntry(basicRow, getRhsOffset()));
        if (!restrictToNonNegative) {
            coefficients[i] -= mostNegative;
        }
        if (basicRow != null) {
            for (int j = getNumObjectiveFunctions(); j < getNumObjectiveFunctions() + i; j++) {
                if (Math.abs(tableau.getEntry(basicRow, j) - 1) < epsilon) {
                    coefficients[i] = 0;
                    break;
                }
            }
        }
    }
    return new RealPointValuePair(coefficients, f.getValue(coefficients));
}
