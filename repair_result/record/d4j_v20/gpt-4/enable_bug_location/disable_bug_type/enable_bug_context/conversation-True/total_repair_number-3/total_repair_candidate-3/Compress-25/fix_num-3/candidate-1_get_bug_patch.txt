public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    in = new PushbackInputStream(inputStream, 512);  // Use a fixed buffer size or appropriate constant, buf.capacity() is likely not initialized yet
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    // haven't read anything so far
}
