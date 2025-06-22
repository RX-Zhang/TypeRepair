public double[] repairAndDecode(final double[] x) {
    if (boundaries == null) {
        return decode(x);
    }
    final double[] repaired = new double[x.length];
    for (int i = 0; i < x.length; i++) {
        double xi = x[i];
        if (xi < boundaries[0][i]) {
            xi = boundaries[0][i];
        } else if (xi > boundaries[1][i]) {
            xi = boundaries[1][i];
        }
        repaired[i] = xi;
    }
    return decode(repaired);
}
