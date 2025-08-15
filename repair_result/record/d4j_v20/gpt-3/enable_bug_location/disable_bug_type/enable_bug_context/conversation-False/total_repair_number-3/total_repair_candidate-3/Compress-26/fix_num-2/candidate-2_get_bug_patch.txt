public static long skip(InputStream input, long numToSkip) throws IOException {
    long totalSkipped = 0; // Track total bytes skipped
    while (numToSkip > 0) {
        long skipped = input.skip(numToSkip);
        if (skipped <= 0) { // If nothing was skipped, break
            break;
        }
        totalSkipped += skipped; // Accumulate the skipped bytes
        numToSkip -= skipped; // Reduce the number of bytes left to skip
    }
        
    return totalSkipped; // Return the total number of bytes actually skipped
}
