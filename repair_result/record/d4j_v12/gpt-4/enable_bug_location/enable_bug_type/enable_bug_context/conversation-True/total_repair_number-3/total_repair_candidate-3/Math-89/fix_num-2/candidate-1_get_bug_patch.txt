public void addValue(Object v) {
    if (v == null) {
        throw new IllegalArgumentException("Null value not allowed.");
    }
    if (!(v instanceof Comparable)) {
        throw new IllegalArgumentException("Value must be Comparable.");
    }
    addValue((Comparable<?>) v);
}
