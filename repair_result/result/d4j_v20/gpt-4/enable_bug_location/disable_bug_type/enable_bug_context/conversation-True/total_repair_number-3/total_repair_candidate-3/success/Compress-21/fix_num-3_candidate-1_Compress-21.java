private void writeBits(final DataOutput header, final BitSet bits, final int length) throws IOException {
    int cache = 0;
    int shift = 8;
    for (int i = 0; i < length; i++) {
        cache |= ((bits.get(i) ? 1 : 0) << --shift);
        if (shift == 0) {
            header.write(cache);
            shift = 8;
            cache = 0;
        }
    }
    if (shift != 8) {
        header.write(cache);
    }
}
