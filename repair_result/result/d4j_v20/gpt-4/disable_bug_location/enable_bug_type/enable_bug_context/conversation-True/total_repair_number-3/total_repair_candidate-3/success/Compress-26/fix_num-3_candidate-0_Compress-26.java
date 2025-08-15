public static long skip(InputStream input, long numToSkip) throws IOException {
    if (numToSkip <= 0) {
        return 0;
    }
    long remaining = numToSkip;
    byte[] skipBuffer = new byte[(int) Math.min(SKIP_BUF_SIZE, remaining)];
    while (remaining > 0) {
        int toRead = (int) Math.min(skipBuffer.length, remaining);
        int read = input.read(skipBuffer, 0, toRead);
        if (read == -1) {
            break;
        }
        remaining -= read;
    }
    return numToSkip - remaining;
}
