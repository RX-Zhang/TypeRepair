public void addValue(Object v) {
    if (v == null) {
        throw new IllegalArgumentException("Value cannot be null.");
    }
    if (!(v instanceof Comparable)) {
        throw new IllegalArgumentException("Value must be Comparable.");
    }
    addValue((Comparable<?>) v);
}
