public static long skip(InputStream input, long numToSkip) throws IOException {
    long available = numToSkip;
    byte[] skipBuffer = new byte[4096]; // Buffer size for skipping by reading
    while (numToSkip > 0) {
        long skipped = input.skip(numToSkip);
        if (skipped > 0) {
            numToSkip -= skipped;
            continue;
        }
        // If skip() returns 0, try to read and discard bytes
        int toRead = (int) Math.min(numToSkip, skipBuffer.length);
        int read = input.read(skipBuffer, 0, toRead);
        if (read == -1) {
            break; // EOF reached
        }
        numToSkip -= read;
    }
    
    return available - numToSkip;
}
