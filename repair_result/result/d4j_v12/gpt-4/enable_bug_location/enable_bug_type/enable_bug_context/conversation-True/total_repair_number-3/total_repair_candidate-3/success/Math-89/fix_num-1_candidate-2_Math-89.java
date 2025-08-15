public void addValue(Object v) {
    if (!(v instanceof Comparable)) {
        throw new IllegalArgumentException("Value must be Comparable.");
    }
    addValue((Comparable<?>) v);
}
