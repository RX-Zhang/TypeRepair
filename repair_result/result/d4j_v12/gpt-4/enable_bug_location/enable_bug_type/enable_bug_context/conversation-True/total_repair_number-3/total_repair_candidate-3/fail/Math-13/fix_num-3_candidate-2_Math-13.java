private RealMatrix squareRoot(RealMatrix m) {
    if (m == null) {
        // if weight matrix is null, return identity to avoid OutOfMemoryError
        return MatrixUtils.createRealIdentityMatrix(cols);
    }
    final EigenDecomposition dec = new EigenDecomposition(m);
    return dec.getSquareRoot();
}
