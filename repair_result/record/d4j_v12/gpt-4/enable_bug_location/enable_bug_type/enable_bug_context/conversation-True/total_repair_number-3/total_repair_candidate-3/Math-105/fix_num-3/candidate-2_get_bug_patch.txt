public double getSumSquaredErrors() {
    if (n < 2) {
        return Double.NaN; // not enough data to compute SSE
    }
    double slope = getSlope();
    if (Double.isNaN(slope)) {
        // If slope is NaN (not enough variation in x), SSE is total sum of squares of y around mean
        return sumYY;
    }
    double intercept = ybar - slope * xbar;
    double sse = 0.0;
    // SSE = sum of squared residuals = sum of (y_i - (intercept + slope * x_i))^2
    // However, sumYY, sumXY, sumXX are incremental calculations; here we use formula:
    // SSE = sumYY - 2 * slope * sumXY - 2 * intercept * sumY + slope * slope * sumXX + 2 * intercept * slope * sumX + n * intercept * intercept
    // but simpler and numerically stable approach is:
    sse = sumYY + n * intercept * intercept + slope * slope * sumXX + 2 * intercept * slope * sumX - 2 * intercept * sumY - 2 * slope * sumXY;
    // SSE might be slightly negative due to floating point errors, so ensure non-negative result
    if (sse < 0) {
        sse = 0.0;
    }
    return sse;
}
