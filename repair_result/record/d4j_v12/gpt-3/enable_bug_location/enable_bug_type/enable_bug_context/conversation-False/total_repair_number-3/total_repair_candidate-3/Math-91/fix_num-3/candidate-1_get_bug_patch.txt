public int compareTo(Fraction object) {
    if (object == null) {
        throw new NullPointerException("The fraction to compare must not be null");
    }
    double nOd = doubleValue();
    double dOn = object.doubleValue();
    return Double.compare(nOd, dOn);
}
