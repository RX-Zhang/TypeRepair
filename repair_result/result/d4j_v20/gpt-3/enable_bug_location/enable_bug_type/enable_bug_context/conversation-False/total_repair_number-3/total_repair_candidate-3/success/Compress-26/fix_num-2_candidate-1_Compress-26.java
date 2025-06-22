public static long skip(InputStream input, long numToSkip) throws IOException {
    long available = numToSkip;
    byte[] skipBuf = null;
    while (numToSkip > 0) {
        long skipped = input.skip(numToSkip);
        if (skipped > 0) {
            numToSkip -= skipped;
            continue;
        }
        // skip returned 0, try reading a byte to see if at EOF
        int b = input.read();
        if (b < 0) {
            break; // EOF reached
        }
        numToSkip--;
    }
    return available - numToSkip;
}
