public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    this.in = new PushbackInputStream(inputStream, ZipArchiveOutputStream.BUFFER_SIZE);
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
}
