public int getLength() {
    if (getBaseValue() == null) {
        return 0; // Boundary check to prevent runtime error
    }
    return ValueUtils.getLength(getBaseValue());
}
