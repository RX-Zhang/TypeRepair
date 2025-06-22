private RealMatrix squareRoot(RealMatrix m) {
    final EigenDecomposition dec = new EigenDecomposition(m);
    RealMatrix squareRootMatrix = dec.getSquareRoot();
    // Ensure resources are released properly
    dec.dispose(); // Assuming dispose method exists to release resources
    return squareRootMatrix;
}
