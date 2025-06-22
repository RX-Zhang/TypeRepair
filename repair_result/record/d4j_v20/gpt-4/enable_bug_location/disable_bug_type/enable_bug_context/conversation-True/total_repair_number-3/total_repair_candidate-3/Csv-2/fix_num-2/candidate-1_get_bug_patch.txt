public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    if (!isMapped(name)) {
        throw new IllegalArgumentException("Mapping does not contain header: " + name);
    }
    final Integer index = mapping.get(name);
    if (index >= size()) {
        throw new IllegalArgumentException("Index for header '" + name + "' is out of range: " + index);
    }
    return values[index.intValue()];
}
