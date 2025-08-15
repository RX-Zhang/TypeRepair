public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        byte[] byteData = (byte[]) object;
        if (canWriteBinaryNatively()) {
            writeBinary(byteData);
            return;
        }
    }
    throw new JsonGenerationException("No native support for writing embedded objects",
            this);
}
