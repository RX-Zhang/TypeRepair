public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    final Integer index = mapping.get(name);
    if (index == null) {
        return null;
    }
    if (index.intValue() < 0 || index.intValue() >= values.length) {
        throw new IllegalArgumentException("Mapping mismatch: index " + index + " is out of bounds for record of length " + values.length);
    }
    return values[index.intValue()];
}
