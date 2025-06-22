public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    // Use a PushbackInputStream with an appropriate buffer size (BUFFER_SIZE)
    in = new PushbackInputStream(inputStream, ZipArchiveOutputStream.BUFFER_SIZE);
}
