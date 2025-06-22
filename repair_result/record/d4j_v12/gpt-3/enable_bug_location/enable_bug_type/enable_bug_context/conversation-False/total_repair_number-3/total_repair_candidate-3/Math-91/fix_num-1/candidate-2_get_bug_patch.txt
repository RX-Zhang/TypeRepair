public int compareTo(Fraction object) {
    if (object == null) {
        throw new NullPointerException("Comparison fraction must not be null");
    }
    int crossProduct = this.numerator * object.denominator - object.numerator * this.denominator;
    return (crossProduct < 0) ? -1 : ((crossProduct > 0) ? 1 : 0);
}
