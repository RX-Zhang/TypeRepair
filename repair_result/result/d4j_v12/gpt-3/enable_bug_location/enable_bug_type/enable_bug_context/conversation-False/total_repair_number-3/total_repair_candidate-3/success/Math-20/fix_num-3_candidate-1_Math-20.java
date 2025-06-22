public double[] repairAndDecode(final double[] x) {
    double[] decoded = decode(x);
    if (boundaries != null) {
        for (int i = 0; i < decoded.length; i++) {
            if (decoded[i] < boundaries[0][i]) {
                decoded[i] = boundaries[0][i];
            } else if (decoded[i] > boundaries[1][i]) {
                decoded[i] = boundaries[1][i];
            }
        }
    }
    return decoded;
}
