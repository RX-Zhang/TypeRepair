InputStream decode(final InputStream in, final Coder coder,
        byte[] password) throws IOException {
    if (coder.properties == null || coder.properties.length < 5) {
        throw new IOException("Invalid coder properties for LZMA decoding");
    }
    byte propsByte = coder.properties[0];
    long dictSize = coder.properties[1] & 0xFFL;
    for (int i = 1; i < 4; i++) {
        dictSize |= ((long)(coder.properties[i + 1] & 0xFF) << (8 * i));
    }
    if (dictSize > LZMAInputStream.DICT_SIZE_MAX) {
        throw new IOException("Dictionary larger than 4GiB maximum size");
    }
    try {
        return new LZMAInputStream(in, -1, propsByte, (int) dictSize);
    } catch (UnsupportedOperationException e) {
        // Wrap and rethrow with more context
        throw new IOException("Unsupported LZMA options: " + e.getMessage(), e);
    }
}
