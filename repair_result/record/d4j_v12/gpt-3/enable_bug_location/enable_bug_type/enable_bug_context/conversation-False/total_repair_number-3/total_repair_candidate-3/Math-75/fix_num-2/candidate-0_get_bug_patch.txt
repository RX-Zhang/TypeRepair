public double getPct(Object v) {
    if (v instanceof Comparable) {
        return getCumPct((Comparable<?>) v) - getCumPct((Comparable<?>) v);
    }
    return 0.0;
}
