public double[] repairAndDecode(final double[] x) {
    double[] repaired = x.clone();

    if (boundaries != null) {
        final double[] lower = boundaries[0];
        final double[] upper = boundaries[1];
        for (int i = 0; i < repaired.length; i++) {
            if (repaired[i] < lower[i]) {
                repaired[i] = lower[i];
            } else if (repaired[i] > upper[i]) {
                repaired[i] = upper[i];
            }
        }
    }

    return decode(repaired);
}
