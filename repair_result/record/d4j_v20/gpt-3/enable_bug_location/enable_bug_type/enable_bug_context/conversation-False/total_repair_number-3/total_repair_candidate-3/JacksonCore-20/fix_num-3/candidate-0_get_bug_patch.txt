public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        // Handle null case
        writeNull(); // Assuming there's a method to write null
        return;
    }
    if (object instanceof byte[]) {
        // Handle byte array case
        writeBinary(Base64Variant.getDefaultVariant(), (byte[]) object, 0, ((byte[]) object).length);
        return;
    }
    // If the object is not supported, throw an exception
    throw new JsonGenerationException("No native support for writing embedded objects", this);
}
