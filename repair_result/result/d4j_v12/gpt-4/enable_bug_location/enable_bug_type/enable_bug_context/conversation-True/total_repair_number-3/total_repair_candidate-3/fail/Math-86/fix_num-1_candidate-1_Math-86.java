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
    lTData = matrix.getData();
    cachedL = null;
    cachedLT = null;

    // Check symmetry and zero out lower triangle elements
    for (int i = 0; i < order; ++i) {

        final double[] lI = lTData[i];

        if (lI[i] < absolutePositivityThreshold) {
            throw new NotPositiveDefiniteMatrixException();
        }

        for (int j = 0; j < i; ++j) {
            final double[] lJ = lTData[j];
            final double lIJ = lI[j];
            final double lJI = lJ[i];
            final double maxDelta =
                relativeSymmetryThreshold * Math.max(Math.abs(lIJ), Math.abs(lJI));
            if (Math.abs(lIJ - lJI) > maxDelta) {
                throw new NotSymmetricMatrixException();
            }
            // Force lower triangle to zero (since we store upper triangular)
            lI[j] = 0;
        }
    }

    // Perform Cholesky decomposition
    for (int i = 0; i < order; ++i) {

        final double[] ltI = lTData[i];

        // Compute sqrt of diagonal element
        if (ltI[i] <= 0) {
            throw new NotPositiveDefiniteMatrixException();
        }
        ltI[i] = Math.sqrt(ltI[i]);
        final double inverse = 1.0 / ltI[i];

        for (int q = i + 1; q < order; ++q) {
            final double[] ltQ = lTData[q];
            double sum = ltQ[i];
            for (int k = 0; k < i; ++k) {
                sum -= ltQ[k] * ltI[k];
            }
            ltQ[i] = sum * inverse;
        }

        // Update the trailing submatrix
        for (int j = i + 1; j < order; ++j) {
            final double[] ltJ = lTData[j];
            final double[] ltIrow = lTData[i];
            for (int k = j; k < order; ++k) {
                ltJ[k] -= ltJ[i] * ltIrow[k];
            }
        }
    }
}
