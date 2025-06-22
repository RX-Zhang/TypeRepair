public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        // Use writeBinary for byte arrays if binary native writing is supported
        if (canWriteBinaryNatively()) {
            writeBinary((byte[]) object);
            return;
        }
    }
    _writeSimpleObject(object);
}
