private RealMatrix squareRoot(RealMatrix m) {
    final EigenDecomposition dec = new EigenDecomposition(m);
    try {
        return dec.getSquareRoot();
    } finally {
        // Ensure resources are released if necessary
        dec.dispose(); // Hypothetical method to release resources, if applicable
    }
}
