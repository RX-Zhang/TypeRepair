public ChecksumCalculatingInputStream(final Checksum checksum, final InputStream in) {
    if (checksum == null) {
        throw new IllegalArgumentException("Checksum cannot be null");
    }
    if (in == null) {
        throw new IllegalArgumentException("InputStream cannot be null");
    }

    this.checksum = checksum;
    this.in = in;
}
