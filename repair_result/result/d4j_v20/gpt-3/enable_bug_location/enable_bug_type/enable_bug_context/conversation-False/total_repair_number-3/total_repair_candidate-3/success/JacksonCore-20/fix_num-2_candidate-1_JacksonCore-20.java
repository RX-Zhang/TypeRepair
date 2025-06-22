public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        // Handle null case
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        // Handle byte array case
        writeBinary(Base64Variants.getDefaultVariant(), (byte[]) object, 0, ((byte[]) object).length);
        return;
    }
    // For other types, throw an exception
    throw new JsonGenerationException("No native support for writing embedded objects", this);
}
