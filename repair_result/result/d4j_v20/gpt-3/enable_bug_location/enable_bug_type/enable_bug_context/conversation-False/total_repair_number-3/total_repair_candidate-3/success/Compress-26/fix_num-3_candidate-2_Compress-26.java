public static long skip(InputStream input, long numToSkip) throws IOException {
    long available = numToSkip;
    byte[] skipBuf = new byte[SKIP_BUF_SIZE];
    while (numToSkip > 0) {
        long skipped = input.skip(numToSkip);
        if (skipped > 0) {
            numToSkip -= skipped;
            continue;
        }
        // skip() returned 0, try to read and discard bytes instead
        int read = input.read(skipBuf, 0, (int) Math.min(numToSkip, skipBuf.length));
        if (read == -1) {
            break; // EOF
        }
        numToSkip -= read;
    }

    return available - numToSkip;
}
