public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        if (canWriteBinaryNatively()) {
            writeBinary((byte[]) object);
            return;
        }
    }
    // fallback: try to handle simple types, or throw
    _writeSimpleObject(object);
}
