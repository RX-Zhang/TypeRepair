public double[] repairAndDecode(final double[] x) {
    // Repair to enforce boundaries if defined
    if (boundaries != null) {
        final double[] repaired = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            if (x[i] < boundaries[0][i]) {
                repaired[i] = boundaries[0][i];
            } else if (x[i] > boundaries[1][i]) {
                repaired[i] = boundaries[1][i];
            } else {
                repaired[i] = x[i];
            }
        }
        return decode(repaired);
    }
    return decode(x);
}
