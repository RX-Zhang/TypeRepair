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
        // fallback is to write Base64-encoded string
        writeString(Base64Variants.getDefaultVariant().encode((byte[]) object));
        return;
    }
    _writeSimpleObject(object);
}
