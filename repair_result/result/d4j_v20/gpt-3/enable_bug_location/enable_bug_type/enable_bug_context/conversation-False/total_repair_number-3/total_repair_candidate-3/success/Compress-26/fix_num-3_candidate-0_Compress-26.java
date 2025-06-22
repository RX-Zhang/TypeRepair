public static long skip(InputStream input, long numToSkip) throws IOException {
    long remaining = numToSkip;
    byte[] skipBuffer = new byte[SKIP_BUF_SIZE];
    while (remaining > 0) {
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
            continue;
        }
        // skip returned 0, try to read and discard bytes instead
        int read = input.read(skipBuffer, 0, (int) Math.min(skipBuffer.length, remaining));
        if (read == -1) {
            break; // EOF reached
        }
        remaining -= read;
    }
    return numToSkip - remaining;
}
