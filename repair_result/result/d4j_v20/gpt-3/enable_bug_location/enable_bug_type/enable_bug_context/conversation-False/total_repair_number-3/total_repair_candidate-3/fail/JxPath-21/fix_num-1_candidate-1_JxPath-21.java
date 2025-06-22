public int getLength() {
    Object baseValue = getBaseValue();
    if (baseValue == null) {
        return 0; // Return 0 if baseValue is null to prevent runtime error
    }
    return ValueUtils.getLength(baseValue);
}
