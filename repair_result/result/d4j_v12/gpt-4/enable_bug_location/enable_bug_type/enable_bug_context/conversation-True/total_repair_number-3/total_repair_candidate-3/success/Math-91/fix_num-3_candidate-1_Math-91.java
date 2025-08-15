public int compareTo(Fraction object) {
    long firstNumerator = (long) this.getNumerator() * object.getDenominator();
    long secondNumerator = (long) object.getNumerator() * this.getDenominator();
    return Long.compare(firstNumerator, secondNumerator);
}
