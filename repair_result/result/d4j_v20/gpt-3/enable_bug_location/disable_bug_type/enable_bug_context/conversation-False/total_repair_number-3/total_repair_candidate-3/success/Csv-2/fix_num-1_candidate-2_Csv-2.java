public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    final Integer index = mapping.get(name);
    if (index == null) {
        throw new IllegalArgumentException("Mapping for name '" + name + "' does not exist");
    }
    if (index.intValue() < 0 || index.intValue() >= values.length) {
        throw new IllegalArgumentException("Mapping for name '" + name + "' is outside the record values range");
    }
    return values[index.intValue()];
}
