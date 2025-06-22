public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    if (!isMapped(name)) {
        throw new IllegalArgumentException("Mapping does not contain the specified name: " + name);
    }
    final Integer index = mapping.get(name);
    // Check index bounds to avoid ArrayIndexOutOfBoundsException
    if (index == null || index < 0 || index >= values.length) {
        throw new IllegalArgumentException("Index out of bounds for the specified name: " + name);
    }
    return values[index.intValue()];
}
