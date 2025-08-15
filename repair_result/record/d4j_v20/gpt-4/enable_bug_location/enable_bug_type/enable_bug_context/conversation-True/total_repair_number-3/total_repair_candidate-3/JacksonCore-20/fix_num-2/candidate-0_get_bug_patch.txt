public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        byte[] data = (byte[]) object;
        if (canWriteBinaryNatively()) {
            writeBinary(null, data, 0, data.length);
            return;
        }
    }
    _writeSimpleObject(object);
}
