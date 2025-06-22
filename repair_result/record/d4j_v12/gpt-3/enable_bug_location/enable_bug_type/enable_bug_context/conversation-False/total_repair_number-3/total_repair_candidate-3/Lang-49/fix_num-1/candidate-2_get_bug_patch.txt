public Fraction reduce() {
    if (numerator == 0) {
        return Fraction.ZERO;
    }
    int gcd = greatestCommonDivisor(numerator, denominator);
    if (gcd == 1) {
        return this;
    }
    return Fraction.getFraction(numerator / gcd, denominator / gcd);
}
