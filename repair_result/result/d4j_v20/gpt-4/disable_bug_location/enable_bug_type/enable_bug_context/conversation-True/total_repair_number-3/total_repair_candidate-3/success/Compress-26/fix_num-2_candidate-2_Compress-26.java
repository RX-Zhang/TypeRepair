public static long skip(InputStream input, long numToSkip) throws IOException {
    long totalSkipped = 0;
    while (totalSkipped < numToSkip) {
        long skipped = input.skip(numToSkip - totalSkipped);
        if (skipped > 0) {
            totalSkipped += skipped;
        } else {
            // skip() returned 0, try to read and discard one byte to proceed
            int b = input.read();
            if (b < 0) {
                break;
            }
            totalSkipped++;
        }
    }
    return totalSkipped;
}
