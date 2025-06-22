public int compareTo(Fraction object) {
    long lhsNumerator = (long) this.getNumerator() * object.getDenominator();
    long rhsNumerator = (long) object.getNumerator() * this.getDenominator();
    return Long.compare(lhsNumerator, rhsNumerator);
}
