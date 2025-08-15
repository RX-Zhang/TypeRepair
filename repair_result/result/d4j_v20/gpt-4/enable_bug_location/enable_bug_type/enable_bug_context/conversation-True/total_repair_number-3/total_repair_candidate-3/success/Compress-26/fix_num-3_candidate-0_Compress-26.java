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
        // If skip returns 0, try reading into the buffer to skip bytes
        int read = input.read(skipBuffer, 0, (int)Math.min(skipBuffer.length, remaining));
        if (read == -1) {
            break; // End of stream reached
        }
        remaining -= read;
    }
    return numToSkip - remaining;
}
