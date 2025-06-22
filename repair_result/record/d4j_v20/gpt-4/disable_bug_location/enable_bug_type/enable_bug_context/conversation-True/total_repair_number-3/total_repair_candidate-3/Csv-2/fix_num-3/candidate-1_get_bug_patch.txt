public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    if (!isMapped(name)) {
        throw new IllegalArgumentException("Mapping for name '" + name + "' does not exist");
    }
    if (!isConsistent()) {
        throw new IllegalArgumentException("Record is inconsistent; number of values does not match header mapping");
    }
    final Integer index = mapping.get(name);
    return index != null ? values[index.intValue()] : null;
}
