public double getPct(Object v) {
    if (v instanceof Comparable) {
        return getCumPct((Comparable<?>) v) / getTotalCount();
    }
    return 0.0;
}
