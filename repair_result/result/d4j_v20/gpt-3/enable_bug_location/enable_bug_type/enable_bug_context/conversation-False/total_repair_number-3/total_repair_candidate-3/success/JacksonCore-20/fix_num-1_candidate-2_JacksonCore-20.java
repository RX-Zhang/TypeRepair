public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        // Handle null case correctly
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        // Handle byte array as embedded object
        writeBinary(Base64Variants.getDefaultVariant(), (byte[]) object, 0, ((byte[]) object).length);
        return;
    }
    // Other object types can be handled here if needed
    throw new JsonGenerationException("No native support for writing embedded objects", this);
}
