public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    in = new PushbackInputStream(inputStream, buf.capacity());
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    current = null; // Initialize current to null to ensure it starts as expected
    closed = false; // Ensure the stream is marked as open
    entriesRead = 0; // Initialize entries read count
    hitCentralDirectory = false; // Initialize flag
    lastStoredEntry = null; // Initialize last stored entry
}
