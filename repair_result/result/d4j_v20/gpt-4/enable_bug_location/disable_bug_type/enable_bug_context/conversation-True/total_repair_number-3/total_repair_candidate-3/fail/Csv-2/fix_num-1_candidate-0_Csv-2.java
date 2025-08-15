public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    if (!isMapped(name)) {
        throw new IllegalArgumentException("Mapping '" + name + "' does not exist in record");
    }
    if (!isConsistent()) {
        throw new IllegalStateException("Record is inconsistent: number of values does not match number of headers");
    }
    final Integer index = mapping.get(name);
    return values[index.intValue()];
}
