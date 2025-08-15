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
    // Copy the matrix data to avoid modifying the original matrix
    lTData = new double[order][order];
    for (int i = 0; i < order; i++) {
        System.arraycopy(matrix.getRow(i), 0, lTData[i], 0, order);
    }
    cachedL  = null;
    cachedLT = null;

    // Check symmetry and positivity
    for (int i = 0; i < order; ++i) {

        if (lTData[i][i] < absolutePositivityThreshold) {
            throw new NotPositiveDefiniteMatrixException();
        }
        for (int j = i + 1; j < order; ++j) {
            final double lIJ = lTData[i][j];
            final double lJI = lTData[j][i];
            final double maxDelta =
                relativeSymmetryThreshold * Math.max(Math.abs(lIJ), Math.abs(lJI));
            if (Math.abs(lIJ - lJI) > maxDelta) {
                throw new NotSymmetricMatrixException();
            }
            // Enforce symmetry by copying lJI into lIJ (upper triangle)
            lTData[i][j] = lJI;
            // Zero out the lower triangle since decomposition uses upper triangle
            lTData[j][i] = 0.0;
        }
    }

    // Perform the Cholesky decomposition on the upper triangle data
    for (int i = 0; i < order; ++i) {

        double[] ltI = lTData[i];

        // Check diagonal element
        if (ltI[i] <= 0.0) {
            throw new NotPositiveDefiniteMatrixException();
        }

        ltI[i] = Math.sqrt(ltI[i]);
        final double inverse = 1.0 / ltI[i];

        for (int q = i + 1; q < order; ++q) {
            double[] ltQ = lTData[q];
            ltI[q] *= inverse;
            for (int p = q; p < order; ++p) {
                ltQ[p] -= ltI[q] * ltI[p];
            }
        }

    }

}
