public static long skip(InputStream input, long numToSkip) throws IOException {
    long available = numToSkip;
    while (numToSkip > 0) {
        long skipped = input.skip(numToSkip);
        if (skipped > 0) {
            numToSkip -= skipped;
        } else {
            // If skip returns 0, try to read a single byte to check for EOF
            if (input.read() == -1) {
                break;
            } else {
                numToSkip--;
            }
        }
    }
    return available - numToSkip;
}
