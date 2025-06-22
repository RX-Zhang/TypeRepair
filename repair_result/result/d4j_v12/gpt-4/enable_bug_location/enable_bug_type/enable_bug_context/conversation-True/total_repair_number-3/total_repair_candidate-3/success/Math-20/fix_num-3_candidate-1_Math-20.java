public double[] repairAndDecode(final double[] x) {
    if (boundaries == null) {
        // no bounds given, just decode
        return decode(x);
    }
    final double[] repaired = new double[x.length];
    for (int i = 0; i < x.length; i++) {
        final double lower = boundaries[0][i];
        final double upper = boundaries[1][i];
        if (x[i] < lower) {
            repaired[i] = lower;
        } else if (x[i] > upper) {
            repaired[i] = upper;
        } else {
            repaired[i] = x[i];
        }
    }
    return decode(repaired);
}
