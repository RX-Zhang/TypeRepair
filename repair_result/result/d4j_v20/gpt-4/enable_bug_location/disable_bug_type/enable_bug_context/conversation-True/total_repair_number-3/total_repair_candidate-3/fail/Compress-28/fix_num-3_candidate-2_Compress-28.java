public int read(byte[] buf, int offset, int numToRead) throws IOException {
    int totalRead = 0;

    // Check if we've hit EOF or if the entry offset exceeds the entry size
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    // Ensure there is a current entry to read from
    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    // Adjust the number of bytes to read to not exceed the available bytes
    numToRead = Math.min(numToRead, available());

    // Read data from the input stream
    try {
        totalRead = is.read(buf, offset, numToRead);
        count(totalRead);
        
        // If the read returns -1, we have reached EOF
        if (totalRead == -1) {
            hasHitEOF = true;
        } else {
            entryOffset += totalRead;
        }

        return totalRead;
    } catch (IOException e) {
        // Handle any IOException that might occur during reading
        throw new IOException("Error reading from input stream", e);
    }
}
