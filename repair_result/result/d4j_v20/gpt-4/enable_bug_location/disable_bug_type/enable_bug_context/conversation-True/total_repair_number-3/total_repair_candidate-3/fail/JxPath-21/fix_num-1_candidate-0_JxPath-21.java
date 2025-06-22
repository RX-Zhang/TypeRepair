public int getLength() {
    Object value = getImmediateNode();
    return ValueUtils.getLength(value);
}
