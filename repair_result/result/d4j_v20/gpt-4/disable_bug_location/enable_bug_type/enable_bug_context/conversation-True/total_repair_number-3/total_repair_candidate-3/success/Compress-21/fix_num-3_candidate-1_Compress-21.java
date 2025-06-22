private void writeBits(final DataOutput header, final BitSet bits, final int length) throws IOException {
    int cache = 0;
    int shift = 7;
    for (int i = 0; i < length; i++) {
        cache |= ((bits.get(i) ? 1 : 0) << shift);
        --shift;
        // Correct condition to flush whenever shift drops below 0 (i.e., after 8 bits)
        if (shift < 0) {
            header.write(cache);
            shift = 7;
            cache = 0;
        }
    }
    if (length > 0 && shift < 7) {
        header.write(cache);
    }
}
