public int compareTo(Fraction object) {
    if (object == null) {
        throw new NullPointerException("The fraction to compare must not be null");
    }
    int thisNumerator = this.numerator * object.denominator;
    int otherNumerator = object.numerator * this.denominator;
    return Integer.compare(thisNumerator, otherNumerator);
}
