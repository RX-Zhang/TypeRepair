private Object readResolve() {
    if (hashCode == 0) {
        calculateHashCode(keys);
    }
    return this;
}
