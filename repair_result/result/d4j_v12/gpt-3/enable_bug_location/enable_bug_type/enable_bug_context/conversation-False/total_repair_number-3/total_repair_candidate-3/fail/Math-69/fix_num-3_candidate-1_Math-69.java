public RealMatrix getCorrelationPValues() throws MathException {
    TDistribution tDistribution = new TDistributionImpl(nObs - 2);
    int nVars = correlationMatrix.getColumnDimension();
    double[][] out = new double[nVars][nVars];
    for (int i = 0; i < nVars; i++) {
        for (int j = 0; j < nVars; j++) {
            if (i == j) {
                out[i][j] = 1d; // p-value for correlation with itself should be 1, not 0
            } else {
                double r = correlationMatrix.getEntry(i, j);
                double denom = 1 - r * r;
                if (denom == 0) {
                    // Perfect correlation, p-value is effectively 0
                    out[i][j] = 0d;
                } else {
                    double t = Math.abs(r * Math.sqrt((nObs - 2) / denom));
                    double p = 2 * (1 - tDistribution.cumulativeProbability(t));
                    // Ensure p-value is never negative due to numerical errors
                    out[i][j] = Math.max(p, 0d);
                }
            }
        }
    }
    return new BlockRealMatrix(out);
}
