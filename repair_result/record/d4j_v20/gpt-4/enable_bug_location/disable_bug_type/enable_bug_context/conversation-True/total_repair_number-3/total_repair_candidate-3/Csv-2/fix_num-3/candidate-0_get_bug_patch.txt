public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    if (!isMapped(name)) {
        throw new IllegalArgumentException("Mapping '" + name + "' does not exist");
    }
    final Integer index = mapping.get(name);
    if (index.intValue() >= size()) {
        throw new IllegalArgumentException("Index for '" + name + "' is out of bounds: " + index);
    }
    return values[index.intValue()];
}
