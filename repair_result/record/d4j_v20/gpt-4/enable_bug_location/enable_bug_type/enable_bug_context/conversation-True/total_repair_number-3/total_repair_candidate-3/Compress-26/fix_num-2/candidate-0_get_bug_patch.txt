public static long skip(InputStream input, long numToSkip) throws IOException {
    if (numToSkip < 0) {
        throw new IllegalArgumentException("skip value is negative");
    }
    long remaining = numToSkip;
    long skipped;
    byte[] skipBuffer = new byte[4096];
    while (remaining > 0) {
        skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
            continue;
        }
        // skip() sometimes returns 0; try to read and discard bytes instead
        int read = input.read(skipBuffer, 0, (int)Math.min(skipBuffer.length, remaining));
        if (read == -1) {
            break; // EOF reached
        }
        remaining -= read;
    }
    return numToSkip - remaining;
}
