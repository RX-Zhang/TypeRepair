private RealMatrix squareRoot(RealMatrix m) {
    // Use setup method to initialize weightMatrixSqrt properly
    if (weightMatrixSqrt == null) {
        setUp();
    }
    return weightMatrixSqrt;
}
