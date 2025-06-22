public double getSumSquaredErrors() {
    if (n < 2) {
        return Double.NaN;
    }
    return sumYY - (sumXY * sumXY) / sumXX;
}
