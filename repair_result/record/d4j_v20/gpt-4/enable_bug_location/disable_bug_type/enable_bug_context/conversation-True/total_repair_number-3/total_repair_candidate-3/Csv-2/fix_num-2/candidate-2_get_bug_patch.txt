public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    if (!isMapped(name)) {
        throw new IllegalArgumentException("Mapping for name '" + name + "' does not exist");
    }
    final Integer index = mapping.get(name);
    if (index == null || index.intValue() >= size()) {
        throw new IllegalArgumentException("Index for name '" + name + "' is out of bounds");
    }
    return values[index.intValue()];
}
