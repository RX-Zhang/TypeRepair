private RealMatrix squareRoot(RealMatrix m) {
    if (m == null) {
        return null;
    }
    final EigenDecomposition dec = new EigenDecomposition(m, DEFAULT_SINGULARITY_THRESHOLD);
    return dec.getSquareRoot();
}
