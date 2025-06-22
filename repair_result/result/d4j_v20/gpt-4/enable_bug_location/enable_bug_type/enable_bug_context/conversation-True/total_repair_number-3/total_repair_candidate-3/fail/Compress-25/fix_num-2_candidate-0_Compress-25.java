public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    // Fixed the PushbackInputStream buffer size by using ZipArchiveOutputStream.BUFFER_SIZE instead of buf.capacity()
    in = new PushbackInputStream(inputStream, ZipArchiveOutputStream.BUFFER_SIZE);
    this.allowStoredEntriesWithDataDescriptor =
        allowStoredEntriesWithDataDescriptor;
    // haven't read anything so far
}
