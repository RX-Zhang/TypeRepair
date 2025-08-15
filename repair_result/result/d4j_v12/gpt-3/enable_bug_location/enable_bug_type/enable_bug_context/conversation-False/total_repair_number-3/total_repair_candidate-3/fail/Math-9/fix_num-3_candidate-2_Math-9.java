public Line revert() {
    final Vector3D newDirection = direction.negate(); // Negate the direction for the reverted line
    final Line reverted = new Line(zero, zero.add(newDirection)); // Use add instead of subtract
    return reverted;
}
