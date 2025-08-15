public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    if (!isMapped(name)) {
        throw new IllegalArgumentException("Mapping '" + name + "' is not found");
    }
    final Integer index = mapping.get(name);
    if (index == null || index.intValue() < 0 || index.intValue() >= size()) {
        throw new IllegalArgumentException("Mapping '" + name + "' is out of bounds");
    }
    return values[index.intValue()];
}
