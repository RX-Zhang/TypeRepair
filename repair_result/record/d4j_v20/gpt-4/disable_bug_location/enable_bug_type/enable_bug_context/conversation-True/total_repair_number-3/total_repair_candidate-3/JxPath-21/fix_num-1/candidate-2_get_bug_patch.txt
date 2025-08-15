public int getLength() {
    if (!isActualProperty()) {
        return 0;
    }
    Object baseValue = getBaseValue();
    if (baseValue == null) {
        return 0;
    }
    return ValueUtils.getLength(baseValue);
}
