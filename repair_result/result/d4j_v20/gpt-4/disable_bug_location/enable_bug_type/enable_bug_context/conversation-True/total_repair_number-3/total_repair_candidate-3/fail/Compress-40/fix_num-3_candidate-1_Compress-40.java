public long readBits(final int count) throws IOException {
    if (count < 0 || count > MAXIMUM_CACHE_SIZE) {
        throw new IllegalArgumentException("count must not be negative or greater than " + MAXIMUM_CACHE_SIZE);
    }
    while (bitsCachedSize < count) {
        final long nextByte = in.read();
        if (nextByte < 0) {
            // If no bits are cached, return -1 to indicate end of stream
            if (bitsCachedSize == 0) {
                return -1;
            }
            // If some bits remain but can't fulfill count, return whatever bits left
            final long bitsOut = byteOrder == ByteOrder.LITTLE_ENDIAN
                    ? (bitsCached & MASKS[bitsCachedSize])
                    : (bitsCached >> (bitsCachedSize - count)) & MASKS[bitsCachedSize];
            bitsCachedSize = 0;
            bitsCached = 0;
            return bitsOut;
        }
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            bitsCached |= (nextByte << bitsCachedSize);
        } else {
            bitsCached <<= 8;
            bitsCached |= nextByte;
        }
        bitsCachedSize += 8;
    }

    final long bitsOut;
    if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
        bitsOut = (bitsCached & MASKS[count]);
        bitsCached >>>= count;
    } else {
        bitsOut = (bitsCached >> (bitsCachedSize - count)) & MASKS[count];
        bitsCached &= ~(MASKS[count] << (bitsCachedSize - count));
    }
    bitsCachedSize -= count;
    return bitsOut;
}
