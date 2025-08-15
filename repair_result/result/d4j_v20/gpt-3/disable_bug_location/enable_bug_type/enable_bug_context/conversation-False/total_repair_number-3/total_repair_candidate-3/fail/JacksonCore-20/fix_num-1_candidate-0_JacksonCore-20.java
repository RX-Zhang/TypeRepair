public void writeEmbeddedObject(Object object) throws IOException {
    if (object == null) {
        writeNull();
        return;
    }
    if (object instanceof byte[]) {
        writeBinary((byte[]) object);
        return;
    }
    // fallback to _writeSimpleObject for basic types
    _writeSimpleObject(object);

    // if _writeSimpleObject does not handle, it throws IllegalStateException
    // so no need to throw here again.
}
