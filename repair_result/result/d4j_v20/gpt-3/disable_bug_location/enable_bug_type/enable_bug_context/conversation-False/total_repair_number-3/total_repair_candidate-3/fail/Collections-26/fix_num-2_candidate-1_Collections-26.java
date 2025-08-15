private Object readResolve() {
    calculateHashCode();
    return this;
}

private void calculateHashCode() {
    int total = 0;
    for (final Object key : keys) {
        if (key != null) {
            total ^= key.hashCode();
        }
    }
    hashCode = total;
}
