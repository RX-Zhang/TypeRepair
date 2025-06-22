public Dfp multiply(final int x) {
    if (x == 0) {
        // Handle multiplication by zero explicitly to preserve sign when needed
        Dfp result = newInstance(getZero());
        result.sign = (byte)((sign == 1) ? 1 : -1);
        return result;
    }
    return multiplyFast(x);
}
