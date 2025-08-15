public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        writeBinary((byte[]) object);
        return;
    }
    throw new JsonGenerationException("No native support for writing embedded objects (type passed: "
            + object.getClass().getName() + ")", this);
}
