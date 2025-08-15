public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    if (!isMapped(name)) {
        throw new IllegalArgumentException("Mapping does not contain header: " + name);
    }
    final Integer index = mapping.get(name);
    if (!isConsistent() && (index == null || index >= values.length)) {
        throw new IllegalArgumentException("Inconsistent record: index out of bounds for header: " + name);
    }
    return index != null ? values[index.intValue()] : null;
}
