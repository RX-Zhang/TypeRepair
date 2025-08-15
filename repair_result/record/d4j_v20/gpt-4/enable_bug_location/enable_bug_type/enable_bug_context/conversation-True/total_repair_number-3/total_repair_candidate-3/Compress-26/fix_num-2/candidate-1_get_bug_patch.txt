public static long skip(InputStream input, long numToSkip) throws IOException {
    if (numToSkip < 0) {
        throw new IllegalArgumentException("Skip count must be non-negative");
    }
    long remaining = numToSkip;
    byte[] skipBuffer = new byte[4096];
    while (remaining > 0) {
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
        } else {
            int read = input.read(skipBuffer, 0, (int) Math.min(skipBuffer.length, remaining));
            if (read == -1) {
                break;
            }
            remaining -= read;
        }
    }
    return numToSkip - remaining;
}
