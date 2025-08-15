private RealMatrix squareRoot(RealMatrix m) {
    final EigenDecomposition dec = new EigenDecomposition(m);
    try {
        return dec.getSquareRoot();
    } finally {
        dec.dispose();
    }
}
