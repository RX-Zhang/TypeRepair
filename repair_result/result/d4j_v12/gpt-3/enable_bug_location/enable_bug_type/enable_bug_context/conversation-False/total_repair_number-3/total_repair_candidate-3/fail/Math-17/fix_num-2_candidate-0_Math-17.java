public Dfp multiply(final int x) {
        // Implement input validation for x
        if (x < 0 || x >= RADIX) {
            field.setIEEEFlagsBits(DfpField.FLAG_INVALID);
            Dfp result = newInstance(getZero());
            result.nans = QNAN;
            return dotrap(DfpField.FLAG_INVALID, MULTIPLY_TRAP, newInstance(getZero()), result);
        }
        return multiplyFast(x);
}
