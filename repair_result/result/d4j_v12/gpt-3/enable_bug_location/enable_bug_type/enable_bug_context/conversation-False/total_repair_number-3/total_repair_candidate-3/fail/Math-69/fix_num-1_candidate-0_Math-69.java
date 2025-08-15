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
                // To prevent division by zero or negative inside sqrt, add small eps or check bounds
                double denom = 1 - r * r;
                if (denom <= 0) {
                    denom = Double.MIN_VALUE; // avoids division by zero or negative sqrt
                }
                double t = Math.abs(r * Math.sqrt((nObs - 2) / denom));
                out[i][j] = 2 * (1 - tDistribution.cumulativeProbability(t));
                // ensure p-values are not negative due to numerical errors
                if (out[i][j] < 0) {
                    out[i][j] = 0d;
                }
            }
        }
    }
    return new BlockRealMatrix(out);
}
