public double getSumSquaredErrors() {
    if (n < 2) {
        return Double.NaN; // not enough data to compute SSE
    }
    double slope = getSlope();
    if (Double.isNaN(slope)) {
        // If slope is NaN (e.g. no variation in x), SSE = sumYY
        return sumYY;
    }
    return sumYY - slope * sumXY;
}
