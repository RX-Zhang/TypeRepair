public long readBits(final int count) throws IOException {
    if (count < 0 || count > MAXIMUM_CACHE_SIZE) {
        throw new IllegalArgumentException("count must not be negative or greater than " + MAXIMUM_CACHE_SIZE);
    }
    while (bitsCachedSize < count) {
        final int nextByte = in.read();
        if (nextByte < 0) {
            // When no more data is available, return -1
            return -1;
        }
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            bitsCached |= ((long) nextByte << bitsCachedSize);
        } else {
            bitsCached = (bitsCached << 8) | nextByte;
        }
        bitsCachedSize += 8;
    }

    final long bitsOut;
    if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
        bitsOut = bitsCached & MASKS[count];
        bitsCached >>>= count;
    } else {
        bitsOut = (bitsCached >> (bitsCachedSize - count)) & MASKS[count];
        bitsCached &= MASKS[bitsCachedSize - count];
    }
    bitsCachedSize -= count;
    return bitsOut;
}
