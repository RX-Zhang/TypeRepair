public double getSumSquaredErrors() {
    if (n < 3) {
        return Double.NaN;
    }
    return sumYY - sumXY * sumXY / sumXX;
}
