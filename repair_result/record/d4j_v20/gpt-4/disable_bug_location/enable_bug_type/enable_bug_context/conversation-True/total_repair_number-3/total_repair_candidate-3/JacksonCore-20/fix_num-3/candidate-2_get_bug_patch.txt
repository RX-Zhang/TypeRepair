public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (canWriteBinaryNatively() && object instanceof byte[]) {
        writeBinary((byte[]) object);
        return;
    }
    _writeSimpleObject(object);
}
