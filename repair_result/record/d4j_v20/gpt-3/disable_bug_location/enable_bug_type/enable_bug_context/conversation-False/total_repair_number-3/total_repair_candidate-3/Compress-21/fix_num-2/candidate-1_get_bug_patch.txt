private void writeBits(final DataOutput header, final BitSet bits, final int length) throws IOException {
    int cache = 0;
    int shift = 8; // should start with 8 bits, not 7
    for (int i = 0; i < length; i++) {
        cache <<= 1;
        if (bits.get(i)) {
            cache |= 1;
        }
        shift--;
        if (shift == 0) {
            header.write(cache);
            shift = 8;
            cache = 0;
        }
    }
    if (shift != 8) { // if partially filled byte, shift to the left to fill remaining bits with 0
        cache <<= shift;
        header.write(cache);
    }
}
