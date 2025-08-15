public Line revert() {
    final Line reverted = new Line(zero.add(direction), zero); // Corrected the logic to create a line in the opposite direction
    return reverted;
}
