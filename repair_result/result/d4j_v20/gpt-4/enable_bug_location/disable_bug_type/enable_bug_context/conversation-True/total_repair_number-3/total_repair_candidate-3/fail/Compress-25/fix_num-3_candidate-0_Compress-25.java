public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    this.in = new PushbackInputStream(inputStream, 512); // Use a fixed buffer size or properly initialize buf before use
    // haven't read anything so far
}
