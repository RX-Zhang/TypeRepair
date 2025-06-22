public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        // Base64-encode and write as a String
        writeBinary((byte[]) object);
        return;
    }
    throw new JsonGenerationException("No native support for writing embedded objects of type "+object.getClass().getName(),
            this);
}
