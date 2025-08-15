public int compareTo(Fraction object) {
    // Compare by cross-multiplying to avoid floating point precision errors
    long lhs = (long) this.getNumerator() * object.getDenominator();
    long rhs = (long) object.getNumerator() * this.getDenominator();
    return (lhs < rhs) ? -1 : ((lhs > rhs) ? 1 : 0);
}
