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

        if (lTData[i][i] < absolutePositivityThreshold) {
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

    // perform the Cholesky decomposition
    for (int i = 0; i < order; ++i) {

        final double[] ltI = lTData[i];

        // check diagonal element for positive definiteness
        if (ltI[i] <= 0) {
            throw new NotPositiveDefiniteMatrixException();
        }

        ltI[i] = Math.sqrt(ltI[i]);
        final double inverse = 1.0 / ltI[i];

        for (int q = i + 1; q < order; ++q) {
            final double[] ltQ = lTData[q];
            double sum = 0.0;
            for (int k = i; k < q; ++k) {
                sum += ltQ[k] * ltI[k];
            }
            ltQ[i] = (ltQ[i] - sum) * inverse;
        }

        // zero out upper triangle elements (above the diagonal)
        for (int j = i + 1; j < order; ++j) {
            lTData[i][j] = 0.0;
        }
    }

}
