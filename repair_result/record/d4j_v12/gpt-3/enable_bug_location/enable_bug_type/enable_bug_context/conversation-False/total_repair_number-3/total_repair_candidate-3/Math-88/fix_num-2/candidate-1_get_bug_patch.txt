protected RealPointValuePair getSolution() {
    double[] coefficients = new double[getOriginalNumDecisionVariables()];
    Integer basicRow;
    double mostNegative = 0; // Initialize mostNegative to 0
    
    for (int i = 0; i < coefficients.length; i++) {
        basicRow = getBasicRow(getNumObjectiveFunctions() + i);
        if (basicRow != null) {
            mostNegative = getEntry(basicRow, getRhsOffset());
        }
        
        coefficients[i] = (basicRow == null ? 0 : mostNegative) -
                          (restrictToNonNegative ? 0 : mostNegative);
        
        if (basicRow != null) {
            for (int j = getNumObjectiveFunctions(); j < getNumObjectiveFunctions() + getOriginalNumDecisionVariables(); j++) {
                if (tableau.getEntry(basicRow, j) == 1) {
                     coefficients[i] = 0;
                     break; // Break out of the loop once we set coefficients[i] to 0
                }
            }
        }
    }
    return new RealPointValuePair(coefficients, f.getValue(coefficients));
}
