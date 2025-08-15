public int compareTo(Fraction object) {
    long lhs = (long) this.getNumerator() * object.getDenominator();
    long rhs = (long) object.getNumerator() * this.getDenominator();
    return Long.compare(lhs, rhs);
}
