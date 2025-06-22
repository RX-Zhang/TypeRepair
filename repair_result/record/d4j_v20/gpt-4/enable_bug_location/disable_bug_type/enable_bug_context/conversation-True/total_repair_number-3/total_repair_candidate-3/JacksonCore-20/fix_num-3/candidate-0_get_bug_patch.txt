public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        if (canWriteBinaryNatively()) {
            writeBinary((byte[]) object);
            return;
        }
        // fallback to base64-encoded String
        writeString(Base64Variants.getDefaultVariant().encode((byte[]) object));
        return;
    }
    _writeSimpleObject(object);
}
