public static long skip(InputStream input, long numToSkip) throws IOException {
    long remaining = numToSkip;
    byte[] skipBuffer = new byte[(int) Math.min(SKIP_BUF_SIZE, remaining)];
    while (remaining > 0) {
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
            continue;
        }
        // skip returned 0, so try to read and discard bytes manually
        int read = input.read(skipBuffer, 0, (int) Math.min(skipBuffer.length, remaining));
        if (read == -1) {
            break; // EOF reached
        }
        remaining -= read;
    }
    return numToSkip - remaining;
}
