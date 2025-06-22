private static ZipLong unixTimeToZipLong(long l) {
    final long TWO_TO_32 = 0x100000000L;
    // Should check for signed 32 bit integer range: -2^31 to 2^31 - 1
    if (l < 0 || l > 0x7FFFFFFFL) {
        throw new IllegalArgumentException("X5455 timestamps must fit in a signed 32 bit integer: " + l);
    }
    return new ZipLong(l);
}
