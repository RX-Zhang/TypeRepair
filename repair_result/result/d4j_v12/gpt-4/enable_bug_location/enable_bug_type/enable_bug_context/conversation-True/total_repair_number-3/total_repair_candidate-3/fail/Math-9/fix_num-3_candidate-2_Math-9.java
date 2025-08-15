public Line revert() {
    final Line reverted = new Line();
    reverted.reset(getOrigin(), getOrigin().add(getDirection().negate()));
    return reverted;
}
