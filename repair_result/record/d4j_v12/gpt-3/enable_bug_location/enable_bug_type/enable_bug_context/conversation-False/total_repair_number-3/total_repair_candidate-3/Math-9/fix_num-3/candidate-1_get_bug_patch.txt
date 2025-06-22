public Line revert() {
    final Line reverted = new Line(zero.add(direction), zero); // Corrected logical flow to create the reverted line
    return reverted;
}
