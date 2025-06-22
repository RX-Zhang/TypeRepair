public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    in = new PushbackInputStream(inputStream, buf.capacity());
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    // haven't read anything so far
    current = null; // Initialize current to null to avoid logical errors
    closed = false; // Ensure the stream is not closed initially
    hitCentralDirectory = false; // Initialize hitCentralDirectory to false
    lastStoredEntry = null; // Initialize lastStoredEntry to null
    entriesRead = 0; // Initialize entriesRead to 0
}
