private RealMatrix squareRoot(RealMatrix m) {
        final EigenDecomposition dec = new EigenDecomposition(m);
        try {
            return dec.getSquareRoot();
        } finally {
            // Ensure resources are released if necessary
            dec.close(); // Assuming EigenDecomposition has a close method to release resources
        }
}
