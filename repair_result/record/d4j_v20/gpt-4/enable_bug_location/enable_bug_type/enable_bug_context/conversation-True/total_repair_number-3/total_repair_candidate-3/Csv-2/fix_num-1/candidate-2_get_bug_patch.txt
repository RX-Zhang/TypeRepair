public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    if (!isMapped(name)) {
        throw new IllegalArgumentException("Mapping does not contain the header: " + name);
    }
    if (!isConsistent()) {
        throw new IllegalArgumentException("CSVRecord is inconsistent with the header mapping");
    }
    final Integer index = mapping.get(name);
    return values[index.intValue()];
}
