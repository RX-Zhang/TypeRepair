public double getPct(Object v) {
    if (v instanceof Comparable) {
        return getCumPct((Comparable<?>) v) / getTotalCount(); // Assuming getTotalCount() returns the total count of all values
    }
    return 0.0;
}
