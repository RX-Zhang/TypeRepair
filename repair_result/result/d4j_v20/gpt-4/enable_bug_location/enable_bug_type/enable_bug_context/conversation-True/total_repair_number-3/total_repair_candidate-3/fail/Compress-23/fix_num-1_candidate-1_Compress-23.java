InputStream decode(final InputStream in, final Coder coder,
        byte[] password) throws IOException {
    // The dictionary size is stored in coder.properties starting at index 1 and spanning 4 bytes (1 to 4).
    if (coder.properties.length < 5) {
        throw new IOException("LZMA properties too short");
    }
    int propsByte = coder.properties[0] & 0xFF;

    long dictSize = 0;
    for (int i = 0; i < 4; i++) {
        dictSize |= ((long)(coder.properties[i + 1] & 0xFF)) << (8 * i);
    }

    if (dictSize > LZMAInputStream.DICT_SIZE_MAX) {
        throw new IOException("Dictionary larger than 4GiB maximum size");
    }

    // Validate propsByte according to LZMA specification. 0<= lc, lp, pb <=4 
    // Here propsByte packs lc, lp, pb in bits, so check:
    int lc = propsByte % 9;
    int remainder = propsByte / 9;
    int lp = remainder % 5;
    int pb = remainder / 5;
    if (lc > 8 || lp > 4 || pb > 4) {
        throw new IOException("Unsupported LZMA properties");
    }

    return new LZMAInputStream(in, -1, coder.properties, 1);
}
