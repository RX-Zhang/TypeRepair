private RealMatrix squareRoot(RealMatrix m) {
    final EigenDecomposition dec = new EigenDecomposition(m);
    try {
        return dec.getSquareRoot();
    } finally {
        // Ensure resources are released if necessary
        dec.dispose(); // Assuming dispose method exists to release resources
    }
}
