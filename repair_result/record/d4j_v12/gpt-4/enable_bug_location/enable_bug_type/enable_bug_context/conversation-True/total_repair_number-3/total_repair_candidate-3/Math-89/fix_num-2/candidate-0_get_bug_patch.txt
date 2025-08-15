public void addValue(Object v) {
    if (!(v instanceof Comparable)) {
        throw new IllegalArgumentException("Input value must be Comparable.");
    }
    addValue((Comparable<?>) v);
}
