public int compareTo(Fraction object) {
    // cross multiply to avoid floating point precision issues
    long lhs = (long) getNumerator() * object.getDenominator();
    long rhs = (long) object.getNumerator() * getDenominator();
    return Long.compare(lhs, rhs);
}
