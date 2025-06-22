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
                double value = (nObs - 2) / (1.0 - r * r);
                // Correct the formula to use double division
                double t = Math.abs(r * Math.sqrt(value));
                out[i][j] = 2 * tDistribution.cumulativeProbability(-t);
            }
        }
    }
    return new BlockRealMatrix(out);
}
