private Integer getBasicRow(final int col) {
    Integer row = null;
    for (int i = getNumObjectiveFunctions(); i < getHeight(); i++) {
        if (!MathUtils.equals(getEntry(i, col), 0.0, epsilon)) {
            if (row == null) {
                row = i;
            } else {
                // If we find more than one non-zero entry, we should return null
                return null; 
            }
        }
    }
    // Ensure that only one basic row is found, otherwise return null
    return row != null ? row : null; 
}
