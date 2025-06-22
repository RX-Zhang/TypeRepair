public int compareTo(Fraction object) {
    long nOd = (long) this.getNumerator() * object.getDenominator();
    long dOn = (long) object.getNumerator() * this.getDenominator();
    return (nOd < dOn) ? -1 : ((nOd > dOn) ? +1 : 0);
}
