public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
    } else if (object instanceof byte[]) {
        if (canWriteBinaryNatively()) {
            byte[] data = (byte[]) object;
            writeBinary(Base64Variants.getDefaultVariant(), data, 0, data.length);
        } else {
            // fallback: encode as Base64 string
            writeString(Base64Variants.getDefaultVariant().encode((byte[]) object));
        }
    } else {
        throw new JsonGenerationException("No native support for writing embedded objects",
                this);
    }
}
