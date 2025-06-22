public CholeskyDecompositionImpl(final RealMatrix matrix,
                                 final double relativeSymmetryThreshold,
                                 final double absolutePositivityThreshold)
    throws NonSquareMatrixException,
           NotSymmetricMatrixException, NotPositiveDefiniteMatrixException {

    if (!matrix.isSquare()) {
        throw new NonSquareMatrixException(matrix.getRowDimension(),
                                           matrix.getColumnDimension());
    }

    final int order = matrix.getRowDimension();
    lTData   = matrix.getData();
    cachedL  = null;
    cachedLT = null;

    // check the matrix before transformation
    for (int i = 0; i < order; ++i) {

        final double[] lI = lTData[i];

        // check diagonal element positivity
        if (lI[i] < absolutePositivityThreshold) {
            throw new NotPositiveDefiniteMatrixException();
        }
        // check off-diagonal elements for symmetry
        for (int j = i + 1; j < order; ++j) {
            final double[] lJ = lTData[j];
            final double lIJ = lI[j];
            final double lJI = lJ[i];
            final double maxDelta =
                relativeSymmetryThreshold * Math.max(Math.abs(lIJ), Math.abs(lJI));
            if (Math.abs(lIJ - lJI) > maxDelta) {
                throw new NotSymmetricMatrixException();
            }
        }
    }

    // Perform Cholesky decomposition
    for (int i = 0; i < order; ++i) {

        final double[] ltI = lTData[i];

        // Calculate diagonal element
        double sumSquares = 0.0;
        for (int k = 0; k < i; ++k) {
            sumSquares += ltI[k] * ltI[k];
        }
        double diag = ltI[i] - sumSquares;
        if (diag <= absolutePositivityThreshold) {
            throw new NotPositiveDefiniteMatrixException();
        }
        ltI[i] = Math.sqrt(diag);

        // Calculate off-diagonal elements
        for (int j = i + 1; j < order; ++j) {
            final double[] ltJ = lTData[j];
            double sumProducts = 0.0;
            for (int k = 0; k < i; ++k) {
                sumProducts += ltJ[k] * ltI[k];
            }
            ltJ[i] = (ltJ[i] - sumProducts) / ltI[i];
        }
    }

    // Zero out the upper triangular elements to keep only lower part
    for (int i = 0; i < order; ++i) {
        final double[] ltI = lTData[i];
        for (int j = i + 1; j < order; ++j) {
            ltI[j] = 0.0;
        }
    }
}
