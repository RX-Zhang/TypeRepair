private static ZipLong unixTimeToZipLong(long l) {
    final long TWO_TO_31 = 0x80000000L;
    final long TWO_TO_32 = 0x100000000L;
    if (l < 0 || l >= TWO_TO_32) {
        throw new IllegalArgumentException("X5455 timestamps must fit in an unsigned 32 bit integer: " + l);
    }
    return new ZipLong(l);
}
