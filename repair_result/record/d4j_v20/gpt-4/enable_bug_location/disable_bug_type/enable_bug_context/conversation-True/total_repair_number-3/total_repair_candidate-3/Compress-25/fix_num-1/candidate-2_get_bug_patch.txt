public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    // Initialize buffer capacity before using it to create PushbackInputStream
    buf = ByteBuffer.allocate(ZipArchiveOutputStream.BUFFER_SIZE);
    in = new PushbackInputStream(inputStream, buf.capacity());
    // haven't read anything so far
}
