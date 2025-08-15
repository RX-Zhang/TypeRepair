public ChecksumCalculatingInputStream(final Checksum checksum, final InputStream in) {
    if (checksum == null) {
        throw new NullPointerException("checksum cannot be null");
    }
    if (in == null) {
        throw new NullPointerException("input stream cannot be null");
    }
    this.checksum = checksum;
    this.in = in;
}
