private RealMatrix squareRoot(RealMatrix m) {
        final EigenDecomposition dec = new EigenDecomposition(m);
        RealMatrix sqrtMatrix = dec.getSquareRoot();
        // Ensure resources are released
        dec.dispose();
        return sqrtMatrix;
}
