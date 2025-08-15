public static long skip(InputStream input, long numToSkip) throws IOException {
    long remaining = numToSkip;
    if (remaining <= 0) {
        return 0;
    }
    byte[] skipBuffer = new byte[(int) Math.min(SKIP_BUF_SIZE, remaining)];
    while (remaining > 0) {
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
        } else {
            // If skip returned 0, try to read and discard bytes instead
            int read = input.read(skipBuffer, 0, (int) Math.min(skipBuffer.length, remaining));
            if (read == -1) {
                break;
            }
            remaining -= read;
        }
    }
    return numToSkip - remaining;
}
