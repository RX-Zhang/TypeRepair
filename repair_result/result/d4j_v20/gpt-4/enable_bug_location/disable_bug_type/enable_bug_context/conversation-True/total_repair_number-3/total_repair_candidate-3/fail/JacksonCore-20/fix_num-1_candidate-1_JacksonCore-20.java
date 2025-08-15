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
    if (object instanceof String) {
        writeString((String) object);
        return;
    }
    // No native support for other types:
    throw new JsonGenerationException("No native support for writing embedded objects",
            this);
}
