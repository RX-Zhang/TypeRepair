private RealMatrix squareRoot(RealMatrix m) {
    if (m == null) {
        return null;
    }
    final EigenDecomposition dec = new EigenDecomposition(m);
    return dec.getSquareRoot();
}
