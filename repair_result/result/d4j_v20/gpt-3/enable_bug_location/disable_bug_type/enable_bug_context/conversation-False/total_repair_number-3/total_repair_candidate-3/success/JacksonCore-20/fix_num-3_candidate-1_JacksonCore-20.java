public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        // write as base64-encoded string
        writeBinary((byte[]) object);
        return;
    }
    throw new JsonGenerationException("No native support for writing embedded objects", this);
}
