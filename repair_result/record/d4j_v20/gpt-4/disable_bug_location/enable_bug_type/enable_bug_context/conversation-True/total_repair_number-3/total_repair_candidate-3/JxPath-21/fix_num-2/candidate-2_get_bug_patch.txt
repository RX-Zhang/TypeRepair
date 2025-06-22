public int getLength() {
    if (value == UNINITIALIZED) {
        value = ValueUtils.getValue(bean, getPropertyName());
    }
    return ValueUtils.getLength(value);
}
