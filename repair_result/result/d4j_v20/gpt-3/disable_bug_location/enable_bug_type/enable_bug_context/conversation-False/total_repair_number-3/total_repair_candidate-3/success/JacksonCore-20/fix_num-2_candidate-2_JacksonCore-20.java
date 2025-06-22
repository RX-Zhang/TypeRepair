public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        writeBinary((byte[]) object);
        return;
    }
    // If other types are passed, no native support
    throw new JsonGenerationException("No native support for writing embedded objects",
            this);
}
