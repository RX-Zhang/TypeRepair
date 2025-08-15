public double[] repairAndDecode(final double[] x) {
    double[] repaired = x;
    if (boundaries != null) {
        repaired = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            double val = x[i];
            if (val < boundaries[0][i]) {
                val = boundaries[0][i];
            } else if (val > boundaries[1][i]) {
                val = boundaries[1][i];
            }
            repaired[i] = val;
        }
    }
    return decode(repaired);
}
