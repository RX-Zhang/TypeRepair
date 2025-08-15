public Dfp multiply(final int x) {
    // Check for NaN before proceeding with multiplication
    if (isNaN() || x < 0 || x >= RADIX) {
        field.setIEEEFlagsBits(DfpField.FLAG_INVALID);
        Dfp result = newInstance(getZero());
        result.nans = QNAN;
        return dotrap(DfpField.FLAG_INVALID, MULTIPLY_TRAP, newInstance(getZero()), result);
    }
    return multiplyFast(x);
}
