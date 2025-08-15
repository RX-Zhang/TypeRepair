public static long skip(InputStream input, long numToSkip) throws IOException {
    if (numToSkip <= 0) {
        return 0;
    }
    long remaining = numToSkip;
    byte[] skipBuffer = new byte[4096];
    while (remaining > 0) {
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
            continue;
        }
        // skip() returned 0: try to read to see if we reached EOF
        int read = input.read(skipBuffer, 0, (int) Math.min(skipBuffer.length, remaining));
        if (read == -1) {
            break;
        }
        remaining -= read;
    }
    return numToSkip - remaining;
}
