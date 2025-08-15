public RealMatrix getCorrelationPValues() throws MathException {
    TDistribution tDistribution = new TDistributionImpl(nObs - 2);
    int nVars = correlationMatrix.getColumnDimension();
    double[][] out = new double[nVars][nVars];
    for (int i = 0; i < nVars; i++) {
        for (int j = 0; j < nVars; j++) {
            if (i == j) {
                out[i][j] = 1d; // p-value of correlation with itself should be 1
            } else {
                double r = correlationMatrix.getEntry(i, j);
                double denom = 1 - r * r;
                // handle possible division by zero or extremely small denom
                if (denom == 0) {
                    out[i][j] = 0d; // perfect correlation implies p-value 0
                } else {
                    double t = Math.abs(r * Math.sqrt((nObs - 2) / denom));
                    out[i][j] = 2 * (1 - tDistribution.cumulativeProbability(t));
                }
            }
        }
    }
    return new BlockRealMatrix(out);
}
