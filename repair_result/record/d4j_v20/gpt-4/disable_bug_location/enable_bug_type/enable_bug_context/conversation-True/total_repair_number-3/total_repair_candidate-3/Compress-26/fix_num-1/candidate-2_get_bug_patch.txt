public static long skip(InputStream input, long numToSkip) throws IOException {
    long remaining = numToSkip;
    byte[] skipBuffer = new byte[1024];
    while (remaining > 0) {
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
            continue;
        }
        // skip() returned 0, try to read and discard bytes instead
        int read = input.read(skipBuffer, 0, (int) Math.min(remaining, skipBuffer.length));
        if (read == -1) { // EOF
            break;
        }
        remaining -= read;
    }
    return numToSkip - remaining;
}
