public Line revert() {
    return new Line(getOrigin(), getOrigin().add(getDirection().negate()));
}
