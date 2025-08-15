public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    in = new PushbackInputStream(inputStream, buf.capacity());
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    // initialize or reset fields as needed for reading entries
    current = null;
    closed = false;
    hitCentralDirectory = false;
    lastStoredEntry = null;
    entriesRead = 0;
}
