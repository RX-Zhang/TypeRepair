public Line revert() {
    final Vector3D newOrigin = zero.add(direction);
    final Vector3D newDirection = direction.negate();
    return new Line(newOrigin, newOrigin.add(newDirection));
}
