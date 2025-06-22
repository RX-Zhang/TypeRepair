public Dfp multiply(final int x) {
    // Handle zero multiplier explicitly to avoid invalid operation in multiplyFast
    if (x == 0) {
        Dfp result = newInstance(getZero());
        result.sign = (byte)((sign == 1) ? 1 : -1);
        return result;
    }
    return multiplyFast(x);
}
