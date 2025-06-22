public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    // Use a fixed buffer size for PushbackInputStream, for example ZipArchiveOutputStream.BUFFER_SIZE
    in = new PushbackInputStream(inputStream, ZipArchiveOutputStream.BUFFER_SIZE);
}
