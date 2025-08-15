public double getPct(Object v) {
    if (v instanceof Comparable) {
        return getCumPct((Comparable<?>) v) / getTotalCount(); // Assuming getTotalCount() returns the total number of values added
    }
    return 0.0; // Return 0.0 for non-comparable objects
}
