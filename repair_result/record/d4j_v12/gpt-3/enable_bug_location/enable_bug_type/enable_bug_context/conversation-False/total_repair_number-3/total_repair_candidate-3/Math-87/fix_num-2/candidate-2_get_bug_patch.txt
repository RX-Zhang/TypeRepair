private Integer getBasicRow(final int col) {
    Integer row = null;
    for (int i = getNumObjectiveFunctions(); i < getHeight(); i++) {
        if (!MathUtils.equals(getEntry(i, col), 0.0, epsilon)) {
            if (row == null) {
                row = i;
            } else {
                return null; // If there is more than one non-zero entry, return null
            }
        }
    }
    return row; // Return the unique row index or null if none is found
}
