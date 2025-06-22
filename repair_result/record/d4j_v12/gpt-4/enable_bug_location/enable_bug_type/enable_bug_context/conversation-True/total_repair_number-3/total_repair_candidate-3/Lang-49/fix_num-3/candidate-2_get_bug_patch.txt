public Fraction reduce() {
    if (numerator == 0) {
        return Fraction.ZERO;
    }
    if (denominator == 1) {
        return this;
    }
    int gcd = greatestCommonDivisor(Math.abs(numerator), denominator);
    if (gcd == 1) {
        return this;
    }
    return Fraction.getFraction(numerator / gcd, denominator / gcd);
}
