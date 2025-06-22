public double getPct(Object v) {
    if (v instanceof Comparable) {
        return getCumPct((Comparable<?>) v) / getTotalCount(); // Adjusting to return the percentage correctly
    }
    return 0.0; // Return 0.0 for non-comparable objects
}
