InputStream decode(final InputStream in, final Coder coder,
        byte[] password) throws IOException {
    if (coder.properties == null || coder.properties.length < 5) {
        throw new IOException("Invalid coder properties for LZMA decoding");
    }

    byte propsByte = coder.properties[0];
    long dictSize = (coder.properties[1] & 0xFFL) |
                    ((coder.properties[2] & 0xFFL) << 8) |
                    ((coder.properties[3] & 0xFFL) << 16) |
                    ((coder.properties[4] & 0xFFL) << 24);

    if (dictSize > LZMAInputStream.DICT_SIZE_MAX) {
        throw new IOException("Dictionary larger than 4GiB maximum size");
    }

    return new LZMAInputStream(in, -1, propsByte, (int) dictSize);
}
