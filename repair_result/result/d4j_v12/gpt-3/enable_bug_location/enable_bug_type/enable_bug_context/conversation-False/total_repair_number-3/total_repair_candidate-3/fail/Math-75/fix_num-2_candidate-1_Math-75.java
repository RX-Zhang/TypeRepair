public double getPct(Object v) {
    if (v instanceof Comparable) {
        return getCumPct((Comparable<?>) v) - getCumPct(getPreviousValue((Comparable<?>) v));
    }
    return 0.0;
}

private Comparable<?> getPreviousValue(Comparable<?> v) {
    // Implement logic to find the previous value in the frequency table
    // This is a placeholder; actual implementation will depend on the structure of freqTable
    return null; // Replace with actual logic
}
