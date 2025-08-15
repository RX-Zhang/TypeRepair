public double[] repairAndDecode(final double[] x) {
    double[] repaired = x.clone();
    if (boundaries != null) {
        for (int i = 0; i < repaired.length; i++) {
            if (repaired[i] < boundaries[0][i]) {
                repaired[i] = boundaries[0][i];
            } else if (repaired[i] > boundaries[1][i]) {
                repaired[i] = boundaries[1][i];
            }
        }
    }
    return decode(repaired);
}
