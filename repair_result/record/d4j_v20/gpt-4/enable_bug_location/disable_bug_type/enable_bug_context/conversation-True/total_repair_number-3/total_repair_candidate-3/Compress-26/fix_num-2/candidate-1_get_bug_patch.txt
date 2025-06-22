public static long skip(InputStream input, long numToSkip) throws IOException {
    long totalSkipped = 0; // Track total bytes skipped
    while (numToSkip > 0) {
        long skipped = input.skip(numToSkip);
        if (skipped == 0) {
            break; // No more bytes can be skipped
        }
        totalSkipped += skipped; // Accumulate the total skipped
        numToSkip -= skipped; // Decrease the remaining bytes to skip
    }
        
    return totalSkipped; // Return the total number of bytes skipped
}
