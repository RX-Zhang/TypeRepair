public long readBits(final int count) throws IOException {
    if (count < 0 || count > MAXIMUM_CACHE_SIZE) {
        throw new IllegalArgumentException("count must not be negative or greater than " + MAXIMUM_CACHE_SIZE);
    }
    while (bitsCachedSize < count) {
        final long nextByte = in.read();
        if (nextByte < 0) {
            return -1; // Return -1 to indicate end of stream
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
    }
    bitsCachedSize -= count;
    
    // Ensure that bitsCached is not left-shifted beyond its maximum size
    if (bitsCachedSize < 0) {
        bitsCachedSize = 0; // Reset to zero if it goes negative
    }
    
    return bitsOut;
}
