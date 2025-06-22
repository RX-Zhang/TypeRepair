public int getLength() {
    Object base = getBaseValue();
    if (base == null) {
        return 0;
    }
    if (base instanceof Map) {
        return ((Map<?, ?>) base).size();
    }
    return ValueUtils.getLength(base);
}
