InputStream decode(final InputStream in, final Coder coder,
        byte[] password) throws IOException {
    if (coder.properties == null || coder.properties.length < 5) {
        throw new IOException("Invalid coder properties for LZMA decoding");
    }
    byte propsByte = coder.properties[0];
    long dictSize = (coder.properties[1] & 0xFFL);
    for (int i = 1; i < 4; i++) {
        dictSize |= (coder.properties[i + 1] & 0xFFL) << (8 * i);
    }
    if (dictSize > LZMAInputStream.DICT_SIZE_MAX) {
        throw new IOException("Dictionary larger than 4GiB maximum size");
    }
    if (dictSize == 0) {
        dictSize = 1 << 23; // default dictionary size (8MiB)
    }
    return new LZMAInputStream(in, -1, propsByte, (int) dictSize);
}
