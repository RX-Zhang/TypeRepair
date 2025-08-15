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
                double divisor = 1 - r * r;
                // Guard against division by zero or near zero to avoid infinite or NaN t value
                if (divisor <= 0) {
                    out[i][j] = 0d;
                } else {
                    double t = Math.abs(r * Math.sqrt((nObs - 2) / divisor));
                    out[i][j] = 2 * (1 - tDistribution.cumulativeProbability(t));
                    // Ensure p-value does not become exactly 0 due to numerical underflow
                    if (out[i][j] == 0d) {
                        out[i][j] = Double.MIN_VALUE;
                    }
                }
            }
        }
    }
    return new BlockRealMatrix(out);
}
