public Line revert() {
    final Line reverted = new Line(zero.add(direction), zero);
    return reverted;
}
