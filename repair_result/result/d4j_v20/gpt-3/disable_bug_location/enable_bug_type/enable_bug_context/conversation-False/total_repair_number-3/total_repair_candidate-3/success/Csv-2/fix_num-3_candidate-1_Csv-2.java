public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    final Integer index = mapping.get(name);
    if (index == null) {
        throw new IllegalArgumentException("Mapping does not contain the key: " + name);
    }
    if (index.intValue() < 0 || index.intValue() >= values.length) {
        throw new IllegalArgumentException("Index for key '" + name + "' is out of bounds: " + index);
    }
    return values[index.intValue()];
}
