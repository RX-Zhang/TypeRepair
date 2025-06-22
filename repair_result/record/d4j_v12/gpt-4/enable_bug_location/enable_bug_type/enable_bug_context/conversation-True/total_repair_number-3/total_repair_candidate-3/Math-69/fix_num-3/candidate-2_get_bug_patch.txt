public RealMatrix getCorrelationPValues() throws MathException {
    TDistribution tDistribution = new TDistributionImpl(nObs - 2);
    int nVars = correlationMatrix.getColumnDimension();
    double[][] out = new double[nVars][nVars];
    for (int i = 0; i < nVars; i++) {
        for (int j = 0; j < nVars; j++) {
            if (i == j) {
                out[i][j] = 0d;
            } else {
                double r = correlationMatrix.getEntry(i, j);
                double df = nObs - 2;
                if (df <= 0 || Double.isNaN(r) || Math.abs(r) >= 1.0) {
                    // Not enough data to compute p-value or invalid correlation
                    out[i][j] = Double.NaN;
                } else {
                    double t = Math.abs(r) * Math.sqrt(df / (1 - r * r));
                    out[i][j] = 2 * (1 - tDistribution.cumulativeProbability(t));
                    if (out[i][j] < 0) {
                        // Correct for any numerical precision issues leading to negative p-value
                        out[i][j] = 0.0;
                    }
                }
            }
        }
    }
    return new BlockRealMatrix(out);
}
