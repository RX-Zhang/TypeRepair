public String get(final String name) {
    if (mapping == null) {
        throw new IllegalStateException(
                "No header mapping was specified, the record values can't be accessed by name");
    }
    final Integer index = mapping.get(name);
    if (index == null) {
        return null; // Return null if the name is not mapped
    }
    if (index < 0 || index >= values.length) {
        throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds for values array of length " + values.length);
    }
    return values[index.intValue()];
}
