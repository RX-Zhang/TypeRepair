public static long skip(InputStream input, long numToSkip) throws IOException {
    if (numToSkip <= 0) {
        return 0;
    }
    long remaining = numToSkip;
    while (remaining > 0) {
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            remaining -= skipped;
            continue;
        }
        // skip() might return 0 even if not EOF, so try to read and discard bytes instead
        int b = input.read();
        if (b < 0) {  // End of stream
            break;
        }
        remaining--;
    }
    return numToSkip - remaining;
}
