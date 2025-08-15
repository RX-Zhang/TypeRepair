public double getSumSquaredErrors() {
    if (sumXX == 0) {
        return Double.NaN; // To handle division by zero if no variation in x
    }
    return sumYY - (sumXY * sumXY / sumXX);
}
