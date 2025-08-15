public int getLength() {
    Object baseValue = getBaseValue();
    if (baseValue == null) {
        return 0; // Boundary check to prevent runtime error
    }
    return ValueUtils.getLength(baseValue);
}
