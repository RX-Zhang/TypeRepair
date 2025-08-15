public int getLength() {
    Object baseValue = getImmediateNode();
    return ValueUtils.getLength(baseValue);
}
