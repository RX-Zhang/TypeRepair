public static long skip(InputStream input, long numToSkip) throws IOException {
    long totalSkipped = 0;
    if (numToSkip < 0) {
        throw new IllegalArgumentException("numToSkip must not be negative");
    }
    while (numToSkip > 0) {
        long skipped = input.skip(numToSkip);
        if (skipped > 0) {
            numToSkip -= skipped;
            totalSkipped += skipped;
            continue;
        }
        // skip returned 0, try to read a byte to see if at EOF
        int b = input.read();
        if (b == -1) {
            break; // EOF
        }
        numToSkip--;
        totalSkipped++;
    }
    return totalSkipped;
}
