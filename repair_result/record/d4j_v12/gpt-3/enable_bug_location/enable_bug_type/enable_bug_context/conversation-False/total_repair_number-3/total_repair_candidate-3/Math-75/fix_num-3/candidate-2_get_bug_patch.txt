public double getPct(Object v) {
    if (v instanceof Comparable) {
        return getCumPct((Comparable<?>) v) / getTotalCount(); // Ensure we calculate the percentage based on total count
    }
    return 0.0; // Return 0.0 for non-comparable objects
}
