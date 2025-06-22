private static ZipLong unixTimeToZipLong(long l) {
    final long TWO_TO_31 = 0x80000000L; // signed 32-bit max positive value is 2^31-1
    if (l < 0 || l >= TWO_TO_31) {
        throw new IllegalArgumentException("X5455 timestamps must fit in a signed 32 bit integer: " + l);
    }
    return new ZipLong(l);
}
