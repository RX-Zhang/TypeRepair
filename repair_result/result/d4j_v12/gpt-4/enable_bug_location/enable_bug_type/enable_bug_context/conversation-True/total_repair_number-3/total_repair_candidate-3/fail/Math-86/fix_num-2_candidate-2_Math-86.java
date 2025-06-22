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
    // Make a deep copy of the data to avoid modifying the original matrix
    lTData = new double[order][order];
    for (int i = 0; i < order; ++i) {
        System.arraycopy(matrix.getRow(i), 0, lTData[i], 0, order);
    }
    cachedL  = null;
    cachedLT = null;

    // check the matrix before transformation
    for (int i = 0; i < order; ++i) {

        final double[] lI = lTData[i];

        if (lTData[i][i] < absolutePositivityThreshold) {
            throw new NotPositiveDefiniteMatrixException();
        }
        // check off-diagonal elements (and enforce symmetry by averaging)
        for (int j = i + 1; j < order; ++j) {
            final double[] lJ = lTData[j];
            final double lIJ = lI[j];
            final double lJI = lJ[i];
            final double maxDelta =
                relativeSymmetryThreshold * Math.max(Math.abs(lIJ), Math.abs(lJI));
            if (Math.abs(lIJ - lJI) > maxDelta) {
                throw new NotSymmetricMatrixException();
            }
            // enforce symmetry by averaging
            final double average = 0.5 * (lIJ + lJI);
            lI[j] = average;
            lJ[i] = average;
        }
    }

    // transform the matrix
    for (int i = 0; i < order; ++i) {

        final double[] ltI = lTData[i];

        // check diagonal element
        if (ltI[i] <= 0.0) {
            throw new NotPositiveDefiniteMatrixException();
        }

        ltI[i] = Math.sqrt(ltI[i]);
        final double inverse = 1.0 / ltI[i];

        for (int q = i + 1; q < order; ++q) {     // fix loop direction and bounds: q should run from i+1 to order-1
            ltI[q] *= inverse;
            final double[] ltQ = lTData[q];
            for (int p = q; p < order; ++p) {
                ltQ[p] -= ltI[q] * ltI[p];
            }
        }
    }

}
