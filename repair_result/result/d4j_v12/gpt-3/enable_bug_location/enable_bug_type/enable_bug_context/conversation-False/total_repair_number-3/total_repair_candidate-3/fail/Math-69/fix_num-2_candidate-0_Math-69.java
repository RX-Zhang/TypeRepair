public RealMatrix getCorrelationPValues() throws MathException {
    TDistribution tDistribution = new TDistributionImpl(nObs - 2);
    int nVars = correlationMatrix.getColumnDimension();
    double[][] out = new double[nVars][nVars];
    for (int i = 0; i < nVars; i++) {
        for (int j = 0; j < nVars; j++) {
            if (i == j) {
                out[i][j] = Double.NaN;
            } else {
                double r = correlationMatrix.getEntry(i, j);
                // Avoid division by zero or negative sqrt by ensuring denominator > 0
                double denom = 1 - r * r;
                if (denom <= 0) { 
                    // When r == ±1, p-value is zero
                    out[i][j] = 0d;
                } else {
                    double t = Math.abs(r * Math.sqrt((nObs - 2) / denom));
                    out[i][j] = 2 * (1 - tDistribution.cumulativeProbability(t));
                }
            }
        }
    }
    return new BlockRealMatrix(out);
}
