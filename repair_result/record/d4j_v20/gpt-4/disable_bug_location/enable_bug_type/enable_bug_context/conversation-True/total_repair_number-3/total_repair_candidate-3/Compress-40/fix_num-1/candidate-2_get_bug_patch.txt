public long readBits(final int count) throws IOException {
    if (count < 0 || count > MAXIMUM_CACHE_SIZE) {
        throw new IllegalArgumentException("count must not be negative or greater than " + MAXIMUM_CACHE_SIZE);
    }
    while (bitsCachedSize < count) {
        final long nextByte = in.read();
        if (nextByte < 0) {
            // If no bits are cached, return -1 indicating end of stream
            if (bitsCachedSize == 0) {
                return -1;
            }
            // Return remaining bits in cache if less than requested count
            long bitsOut;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                bitsOut = bitsCached & MASKS[bitsCachedSize];
                bitsCached = 0;
                bitsCachedSize = 0;
            } else {
                bitsOut = (bitsCached >> (bitsCachedSize - count)) & MASKS[bitsCachedSize];
                bitsCached = 0;
                bitsCachedSize = 0;
            }
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
        bitsOut = bitsCached & MASKS[count];
        bitsCached >>>= count;
    } else {
        bitsOut = (bitsCached >> (bitsCachedSize - count)) & MASKS[count];
        bitsCached &= MASKS[bitsCachedSize - count];
    }
    bitsCachedSize -= count;
    return bitsOut;
}
