public static long skip(InputStream input, long numToSkip) throws IOException {
    if (numToSkip <= 0) {
        return 0;
    }
    long totalSkipped = 0;
    byte[] skipBuffer = new byte[4096];

    while (totalSkipped < numToSkip) {
        long remaining = numToSkip - totalSkipped;
        long skipped = input.skip(remaining);
        if (skipped > 0) {
            totalSkipped += skipped;
            continue;
        }

        int read = input.read(skipBuffer, 0, (int) Math.min(skipBuffer.length, remaining));
        if (read == -1) {
            break;
        }
        totalSkipped += read;
    }
    return totalSkipped;
}
