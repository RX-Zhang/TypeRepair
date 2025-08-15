public double getSumSquaredErrors() {
    if (sumXX == 0) {
        return Double.NaN; // Prevent division by zero
    }
    return sumYY - sumXY * sumXY / sumXX;
}
