public ZipArchiveInputStream(InputStream inputStream,
                             String encoding,
                             boolean useUnicodeExtraFields,
                             boolean allowStoredEntriesWithDataDescriptor) {
    zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
    this.useUnicodeExtraFields = useUnicodeExtraFields;
    this.allowStoredEntriesWithDataDescriptor = allowStoredEntriesWithDataDescriptor;
    buf = ByteBuffer.allocate(ZipArchiveOutputStream.DEFAULT_ENCODING_CACHE_SIZE);
    in = new PushbackInputStream(inputStream, buf.capacity());
    // haven't read anything so far
}
