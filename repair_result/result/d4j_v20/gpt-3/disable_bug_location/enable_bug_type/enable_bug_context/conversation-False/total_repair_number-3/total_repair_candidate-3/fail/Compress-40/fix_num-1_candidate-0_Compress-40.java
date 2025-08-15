public long readBits(final int count) throws IOException {
    if (count < 0 || count > MAXIMUM_CACHE_SIZE) {
        throw new IllegalArgumentException("count must not be negative or greater than " + MAXIMUM_CACHE_SIZE);
    }
    while (bitsCachedSize < count) {
        final int nextByte = in.read();
        if (nextByte < 0) {
            // Indicate EOF by returning -1 when no bits are left to read
            if (bitsCachedSize == 0) {
                return -1;
            } else {
                break;
            }
        }
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            bitsCached |= ((long)nextByte << bitsCachedSize);
        } else {
            bitsCached = (bitsCached << 8) | (nextByte & 0xFFL);
        }
        bitsCachedSize += 8;
    }

    if (bitsCachedSize < count) {
        // Not enough bits available, return EOF
        return -1;
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
