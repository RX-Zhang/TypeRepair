public void writeEmbeddedObject(Object object) throws IOException {
    // 01-Sep-2016, tatu: As per [core#318], handle small number of cases
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        if (canWriteBinaryNatively()) {
            writeBinary((byte[]) object);
            return;
        }
    }
    _writeSimpleObject(object);
}
