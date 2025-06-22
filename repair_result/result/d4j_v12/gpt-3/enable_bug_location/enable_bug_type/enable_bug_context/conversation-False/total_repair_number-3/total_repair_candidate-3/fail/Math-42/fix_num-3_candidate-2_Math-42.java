protected RealPointValuePair getSolution() {
  int negativeVarColumn = columnLabels.indexOf(NEGATIVE_VAR_COLUMN_LABEL);
  Integer negativeVarBasicRow = negativeVarColumn >= 0 ? getBasicRow(negativeVarColumn) : null;
  double mostNegative = negativeVarBasicRow == null ? 0 : getEntry(negativeVarBasicRow, getRhsOffset());

  Set<Integer> basicRows = new HashSet<Integer>();
  double[] coefficients = new double[getOriginalNumDecisionVariables()];
  for (int i = 0; i < coefficients.length; i++) {
      int colIndex = columnLabels.indexOf("x" + i);
      if (colIndex < 0) {
        coefficients[i] = 0;
        continue;
      }
      Integer basicRow = getBasicRow(colIndex);

      if (basicRow == null || basicRows.contains(basicRow)) {
          // if multiple variables share the same basic row or not basic, set to 0 or adjust for negativity
          coefficients[i] = (restrictToNonNegative) ? 0 : -mostNegative;
      } else {
          basicRows.add(basicRow);
          coefficients[i] =
              getEntry(basicRow, getRhsOffset()) - (restrictToNonNegative ? 0 : mostNegative);
      }
  }
  return new RealPointValuePair(coefficients, f.getValue(coefficients));
}
