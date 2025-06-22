public static long skip(InputStream input, long numToSkip) throws IOException {
    if (numToSkip <= 0) {
        return 0;
    }
    
    long remaining = numToSkip;
    int maxSkipBufferSize = 2048;
    byte[] skipBuffer = null;

    while (remaining > 0) {
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
            continue;
        }

        // skip() returned 0, try to read to skip bytes manually
        if (skipBuffer == null) {
            skipBuffer = new byte[Math.min(maxSkipBufferSize, (int) remaining)];
        }

        int read = input.read(skipBuffer, 0, (int) Math.min(skipBuffer.length, remaining));
        if (read == -1) { // EOF reached
            break;
        }
        remaining -= read;
    }

    return numToSkip - remaining;
}
