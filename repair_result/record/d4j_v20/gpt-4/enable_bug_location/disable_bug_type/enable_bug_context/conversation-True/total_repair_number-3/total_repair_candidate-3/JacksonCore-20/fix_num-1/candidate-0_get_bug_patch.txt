public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        // write JSON null
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        byte[] data = (byte[]) object;
        if (canWriteBinaryNatively()) {
            // write binary data natively
            writeBinary(data);
            return;
        }
    }
    // try generic simple objects
    _writeSimpleObject(object);
}
