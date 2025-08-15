private RealMatrix squareRoot(RealMatrix m) {
    if (m == null) {
        throw new NullPointerException("Matrix must not be null");
    }
    if (m.getRowDimension() != m.getColumnDimension()) {
        throw new IllegalArgumentException("Matrix must be square");
    }
    // Use a safer eigen decomposition with thresholding to avoid memory issues
    final EigenDecomposition dec = new EigenDecomposition(m, 1e-14);
    return dec.getSquareRoot();
}
