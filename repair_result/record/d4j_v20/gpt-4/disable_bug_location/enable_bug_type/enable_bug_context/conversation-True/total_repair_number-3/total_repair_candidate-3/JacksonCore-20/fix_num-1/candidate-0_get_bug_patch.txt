public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (canWriteBinaryNatively() && object instanceof byte[]) {
        writeBinary((byte[]) object);
        return;
    }
    try {
        _writeSimpleObject(object);
    } catch (IllegalStateException e) {
        throw new JsonGenerationException("No native support for writing embedded objects", this);
    }
}
