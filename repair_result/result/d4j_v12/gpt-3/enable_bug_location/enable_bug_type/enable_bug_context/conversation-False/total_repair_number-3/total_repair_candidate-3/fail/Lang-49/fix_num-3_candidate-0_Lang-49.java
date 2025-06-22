public Fraction reduce() {
    int gcd = greatestCommonDivisor(Math.abs(numerator), denominator);
    if (gcd == 1) {
        return this;
    }
    int reducedNumerator = numerator / gcd;
    int reducedDenominator = denominator / gcd;
    // Normalize sign: denominator should always be positive
    if (reducedDenominator < 0) {
        reducedNumerator = -reducedNumerator;
        reducedDenominator = -reducedDenominator;
    }
    if (reducedNumerator == 0) {
        return Fraction.ZERO;
    }
    return Fraction.getFraction(reducedNumerator, reducedDenominator);
}
