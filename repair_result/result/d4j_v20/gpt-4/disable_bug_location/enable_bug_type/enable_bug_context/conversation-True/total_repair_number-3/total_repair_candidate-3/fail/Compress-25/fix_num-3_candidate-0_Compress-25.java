public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    in = new PushbackInputStream(inputStream, buf.capacity());
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    // haven't read anything so far

    // Initialize buf if not initialized before, assuming buf is a ByteBuffer.
    if (buf == null) {
        // Assuming a default buffer size is defined somewhere
        // Using 512 as a buffer size example
        buf = ByteBuffer.allocate(512);
    }
}
