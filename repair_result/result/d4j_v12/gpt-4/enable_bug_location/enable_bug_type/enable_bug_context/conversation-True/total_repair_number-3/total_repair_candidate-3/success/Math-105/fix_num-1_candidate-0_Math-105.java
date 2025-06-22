public double getSumSquaredErrors() {
    if (n < 2) {
        return Double.NaN;
    }
    double residual = sumYY - (sumXY * sumXY) / sumXX;
    return residual < 0 ? 0 : residual;
}
