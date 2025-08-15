public long readBits(final int count) throws IOException {
    if (count < 0 || count > MAXIMUM_CACHE_SIZE) {
        throw new IllegalArgumentException("count must not be negative or greater than " + MAXIMUM_CACHE_SIZE);
    }
    while (bitsCachedSize < count) {
        final long nextByte = in.read();
        if (nextByte < 0) {
            // Return -1 if no bits are left in cache, else return the remaining bits
            if (bitsCachedSize == 0) {
                return -1;
            } else {
                // Return as many bits as available if count requested is more than available bits
                final long bitsOut;
                if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                    bitsOut = bitsCached & MASKS[bitsCachedSize];
                } else {
                    bitsOut = (bitsCached >> (bitsCachedSize - bitsCachedSize)) & MASKS[bitsCachedSize];
                }
                bitsCachedSize = 0;
                bitsCached = 0;
                return bitsOut;
            }
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
        bitsCached &= (MASKS[bitsCachedSize - count]);
    }
    bitsCachedSize -= count;
    return bitsOut;
}
