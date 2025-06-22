public Dfp multiply(final int x) {
    if (x < 0 || x >= RADIX) {
        // For integers outside of [0, RADIX) range, convert to Dfp first
        Dfp dx = newInstance(x);
        return multiply(dx);
    }
    return multiplyFast(x);
}
