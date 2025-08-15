private Integer getBasicRow(final int col) {
    Integer row = null;
    for (int i = getNumObjectiveFunctions(); i < getHeight(); i++) {
        if (!MathUtils.equals(getEntry(i, col), 0.0, epsilon)) {
            if (row == null) {
                row = i;
            } else {
                return null; // More than one non-zero entry found, return null
            }
        }
    }
    return row; // Return the single row with a non-zero entry or null if none found
}
