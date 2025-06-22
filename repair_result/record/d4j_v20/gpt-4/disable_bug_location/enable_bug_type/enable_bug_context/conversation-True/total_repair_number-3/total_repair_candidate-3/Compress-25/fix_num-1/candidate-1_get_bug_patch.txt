public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    in = new PushbackInputStream(inputStream, buf.capacity());
    this.allowStoredEntriesWithDataDescriptor =
        allowStoredEntriesWithDataDescriptor;

    // Initialize buffer capacity if needed
    if (buf == null) {
        // Assuming buffer size should be set here appropriately
        // This depends on the context, but typically something like:
        buf = ByteBuffer.allocate(512); // or appropriate size
    }
}
