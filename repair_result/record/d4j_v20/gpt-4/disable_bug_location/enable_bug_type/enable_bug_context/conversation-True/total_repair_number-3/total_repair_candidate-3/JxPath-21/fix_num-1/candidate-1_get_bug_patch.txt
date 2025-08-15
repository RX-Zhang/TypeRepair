public int getLength() {
    Object baseValue = getBaseValue();
    if (baseValue == PropertyPointer.UNINITIALIZED) {
        return 0;
    }
    return ValueUtils.getLength(baseValue);
}
