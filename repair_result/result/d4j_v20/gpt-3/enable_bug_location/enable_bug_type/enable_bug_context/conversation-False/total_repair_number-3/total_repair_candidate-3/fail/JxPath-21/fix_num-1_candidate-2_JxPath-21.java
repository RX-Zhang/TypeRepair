public int getLength() {
    Object baseValue = getBaseValue();
    if (baseValue == null) {
        // Handle the case when baseValue is null to prevent runtime errors
        return 0; // or an appropriate default value
    }
    return ValueUtils.getLength(baseValue);
}
