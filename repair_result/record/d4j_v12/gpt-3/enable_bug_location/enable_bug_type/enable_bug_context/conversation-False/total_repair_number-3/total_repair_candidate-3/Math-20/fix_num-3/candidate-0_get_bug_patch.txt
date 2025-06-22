public double[] repairAndDecode(final double[] x) {
    final double[] repaired = decode(x);
    if (boundaries != null) {
        for (int i = 0; i < repaired.length; i++) {
            if (repaired[i] < boundaries[0][i]) {
                repaired[i] = boundaries[0][i];
            } else if (repaired[i] > boundaries[1][i]) {
                repaired[i] = boundaries[1][i];
            }
        }
    }
    return repaired;
}
