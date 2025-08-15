public static long skip(InputStream input, long numToSkip) throws IOException {
    if (numToSkip < 0) {
        throw new IllegalArgumentException("Negative skip length not allowed");
    }
    long remaining = numToSkip;
    byte[] skipBuffer = new byte[4096];
    while (remaining > 0) {
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
        } else if (input.read(skipBuffer, 0, (int)Math.min(skipBuffer.length, remaining)) == -1) {
            break;
        } else {
            remaining--;
        }
    }
    return numToSkip - remaining;
}
