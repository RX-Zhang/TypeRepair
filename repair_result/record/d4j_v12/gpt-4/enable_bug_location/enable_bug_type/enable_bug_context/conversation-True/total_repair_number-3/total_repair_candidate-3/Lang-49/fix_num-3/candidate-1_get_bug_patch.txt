public Fraction reduce() {
    if (numerator == 0) {
        return Fraction.ZERO;
    }
    int gcd = greatestCommonDivisor(Math.abs(numerator), denominator);
    if (gcd == 1) {
        return this;
    }
    return Fraction.getReducedFraction(numerator, denominator);
}
