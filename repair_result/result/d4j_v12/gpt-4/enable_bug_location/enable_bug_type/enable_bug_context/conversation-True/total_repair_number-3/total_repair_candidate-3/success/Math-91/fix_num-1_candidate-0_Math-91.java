public int compareTo(Fraction object) {
    // Compare by cross multiplication to avoid precision issues with double
    long lhs = (long) this.numerator * object.getDenominator();
    long rhs = (long) object.getNumerator() * this.denominator;
    return Long.compare(lhs, rhs);
}
