public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        writeBinary((byte[]) object);
        return;
    }
    // For other types, try to handle simple objects
    try {
        _writeSimpleObject(object);
        return;
    } catch (IllegalStateException e) {
        throw new JsonGenerationException("No native support for writing embedded objects", this, e);
    }
}
