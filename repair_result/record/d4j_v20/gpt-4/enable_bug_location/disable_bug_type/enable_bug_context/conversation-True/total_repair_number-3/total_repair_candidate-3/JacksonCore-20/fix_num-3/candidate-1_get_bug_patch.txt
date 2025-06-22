public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        byte[] data = (byte[]) object;
        if (canWriteBinaryNatively()) {
            writeBinary(data);
            return;
        }
    }
    throw new JsonGenerationException("No native support for writing embedded objects",
            this);
}
