public long readBits(final int count) throws IOException {
    if (count < 0 || count > MAXIMUM_CACHE_SIZE) {
        throw new IllegalArgumentException("count must not be negative or greater than " + MAXIMUM_CACHE_SIZE);
    }
    while (bitsCachedSize < count) {
        final long nextByte = in.read();
        if (nextByte < 0) {
            return bitsCachedSize == 0 ? nextByte : -1; // Return -1 if bitsCachedSize is not zero
        }
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            bitsCached |= (nextByte << bitsCachedSize);
        } else {
            bitsCached = (bitsCached << 8) | nextByte; // Corrected: Use assignment instead of compound assignment
        }
        bitsCachedSize += 8;
    }
    
    final long bitsOut;
    if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
        bitsOut = bitsCached & MASKS[count];
        bitsCached >>>= count;
    } else {
        bitsOut = (bitsCached >> (bitsCachedSize - count)) & MASKS[count];
    }
    bitsCachedSize -= count;
    return bitsOut;
}
