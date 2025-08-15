public double density(final double[] vals) throws DimensionMismatchException {
    final int dim = getDimension();
    if (vals.length != dim) {
        throw new DimensionMismatchException(vals.length, dim);
    }

    double exponentTerm = getExponentTerm(vals);
    double detTerm = FastMath.pow(covarianceMatrixDeterminant, -0.5);
    double normTerm = FastMath.pow(2 * FastMath.PI, -dim * 0.5);

    return normTerm * detTerm * exponentTerm;
}
