private RealMatrix squareRoot(RealMatrix m) {
    final EigenDecomposition dec = new EigenDecomposition(m);
    try {
        return dec.getSquareRoot();
    } finally {
        // Ensure resources are released if necessary.
        dec.getEigenvalues(); // This is just an example to show resource management; adjust as needed.
    }
}
