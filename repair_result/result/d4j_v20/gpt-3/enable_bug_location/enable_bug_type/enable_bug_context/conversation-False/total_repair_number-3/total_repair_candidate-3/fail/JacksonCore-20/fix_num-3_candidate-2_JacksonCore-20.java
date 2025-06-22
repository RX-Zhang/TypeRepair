public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        // Handle null case
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        // Handle byte array case
        writeBinary(Base64Variant.getDefaultVariant(), (byte[]) object, 0, ((byte[]) object).length);
        return;
    }
    // Other cases can be handled here if needed
    throw new JsonGenerationException("No native support for writing embedded objects", this);
}
