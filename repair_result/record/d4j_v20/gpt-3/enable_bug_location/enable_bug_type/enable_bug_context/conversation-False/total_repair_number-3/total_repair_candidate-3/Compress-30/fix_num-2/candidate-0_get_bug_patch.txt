public int read(final byte[] dest, final int offs, final int len)
    throws IOException {
    if (offs < 0) {
        throw new IndexOutOfBoundsException("offs(" + offs + ") < 0.");
    }
    if (len < 0) {
        throw new IndexOutOfBoundsException("len(" + len + ") < 0.");
    }
    if (offs + len > dest.length) {
        throw new IndexOutOfBoundsException("offs(" + offs + ") + len("
                                            + len + ") > dest.length(" + dest.length + ").");
    }
    if (this.in == null) {
        throw new IOException("stream closed");
    }

    final int hi = offs + len;
    int destOffs = offs;
    int b;
    while (destOffs < hi && ((b = read0()) >= 0)) {
        dest[destOffs++] = (byte) b;
        count(1);
    }

    // Correcting the return value to handle the case when no bytes are read
    if (destOffs == offs) {
        return 0; // Return 0 instead of -1 when no bytes are read
    }
    return destOffs - offs;
}
