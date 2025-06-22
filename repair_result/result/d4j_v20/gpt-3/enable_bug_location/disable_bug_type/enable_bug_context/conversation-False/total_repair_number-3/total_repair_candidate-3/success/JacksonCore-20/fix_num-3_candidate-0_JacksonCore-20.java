public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
    } else if (object instanceof byte[]) {
        writeBinary((byte[]) object);
    } else {
        throw new JsonGenerationException("No native support for writing embedded objects (type "
                + object.getClass().getName() + ")", this);
    }
}
