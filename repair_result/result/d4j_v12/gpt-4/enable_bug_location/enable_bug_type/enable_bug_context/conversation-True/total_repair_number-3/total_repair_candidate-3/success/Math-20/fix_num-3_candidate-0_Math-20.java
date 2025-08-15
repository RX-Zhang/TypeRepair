public double[] repairAndDecode(final double[] x) {
    if (boundaries == null) {
        return decode(x);
    }
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
