public Line revert() {
    // Correct the logic to use the direction vector directly instead of subtracting it from zero
    final Line reverted = new Line(zero, zero.add(direction.negate()));
    return reverted;
}
