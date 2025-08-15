public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    // PushbackInputStream should be created with a proper buffer size, typically BUFFER_SIZE from ZipArchiveOutputStream
    in = new PushbackInputStream(inputStream, ZipArchiveOutputStream.BUFFER_SIZE);
    // haven't read anything so far
}
