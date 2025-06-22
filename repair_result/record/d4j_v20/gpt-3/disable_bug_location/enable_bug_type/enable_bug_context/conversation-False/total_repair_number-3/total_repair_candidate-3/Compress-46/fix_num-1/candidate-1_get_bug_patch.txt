private static ZipLong unixTimeToZipLong(long l) {
    final long MAX_UNSIGNED_32 = 0xFFFFFFFFL;
    if (l < 0 || l > MAX_UNSIGNED_32) {
        throw new IllegalArgumentException("X5455 timestamps must fit in an unsigned 32 bit integer: " + l);
    }
    return new ZipLong(l);
}
