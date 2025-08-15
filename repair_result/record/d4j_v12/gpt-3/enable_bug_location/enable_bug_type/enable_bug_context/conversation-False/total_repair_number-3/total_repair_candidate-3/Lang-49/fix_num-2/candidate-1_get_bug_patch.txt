public Fraction reduce() {
    if (numerator == 0) {
        return Fraction.ZERO;
    }
    int gcd = greatestCommonDivisor(Math.abs(numerator), Math.abs(denominator));
    if (gcd == 1) {
        return this;
    }
    int newNumerator = numerator / gcd;
    int newDenominator = denominator / gcd;
    if (newDenominator < 0) {
        newNumerator = -newNumerator;
        newDenominator = -newDenominator;
    }
    return Fraction.getFraction(newNumerator, newDenominator);
}
