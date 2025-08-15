public int getLength() {
    Object baseValue = getBaseValue();
    if (baseValue == null) {
        return 0; // Handle the case where baseValue is null
    }
    return ValueUtils.getLength(baseValue);
}
