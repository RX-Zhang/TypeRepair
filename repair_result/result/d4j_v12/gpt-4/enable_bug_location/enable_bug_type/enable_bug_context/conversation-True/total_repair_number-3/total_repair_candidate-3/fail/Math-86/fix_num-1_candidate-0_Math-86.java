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
    lTData = matrix.getData();
    cachedL = null;
    cachedLT = null;

    // check the matrix before transformation
    for (int i = 0; i < order; ++i) {

        final double[] lI = lTData[i];

        // Check diagonal element for positivity threshold
        if (lI[i] < absolutePositivityThreshold) {
            throw new NotPositiveDefiniteMatrixException();
        }
        // check off-diagonal elements (for symmetry)
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

    // Perform the Cholesky decomposition transform in-place in lTData
    for (int i = 0; i < order; ++i) {

        final double[] ltI = lTData[i];

        // update diagonal element
        if (ltI[i] <= 0) {
            throw new NotPositiveDefiniteMatrixException();
        }
        ltI[i] = Math.sqrt(ltI[i]);
        final double inverse = 1.0 / ltI[i];

        for (int q = i + 1; q < order; ++q) {
            ltI[q] *= inverse;
            final double[] ltQ = lTData[q];
            for (int p = q; p < order; ++p) {
                ltQ[p] -= ltI[q] * ltI[p];
            }
        }
    }
    
    // Zero out the elements above the diagonal since we only store lower triangular matrix
    for (int i = 0; i < order; ++i) {
        final double[] ltI = lTData[i];
        for (int j = i + 1; j < order; ++j) {
            ltI[j] = 0.0;
        }
    }
}
