public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    this.in = new PushbackInputStream(inputStream, 512); // buf not initialized here, so use a fixed reasonable buffer size
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    // haven't read anything so far
}
