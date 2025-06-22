public double[] repairAndDecode(final double[] x) {
    if (boundaries == null) {
        return decode(x);
    }
    final double[] repaired = x.clone();
    // Repair the solution if outside boundaries
    for (int i = 0; i < x.length; i++) {
        if (repaired[i] < boundaries[0][i]) {
            repaired[i] = boundaries[0][i];
        } else if (repaired[i] > boundaries[1][i]) {
            repaired[i] = boundaries[1][i];
        }
    }
    return decode(repaired);
}
