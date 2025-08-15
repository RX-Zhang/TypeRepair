public int compareTo(Fraction object) {
    if (object == null) {
        throw new NullPointerException("The fraction to compare must not be null");
    }
    int num1 = this.numerator * object.denominator;
    int num2 = object.numerator * this.denominator;
    return Integer.compare(num1, num2);
}
