public Fraction reduce() {
    int gcd = greatestCommonDivisor(Math.abs(numerator), Math.abs(denominator));
    if (gcd == 1) {
        return this;
    }
    return Fraction.getReducedFraction(numerator / gcd, denominator / gcd);
}
