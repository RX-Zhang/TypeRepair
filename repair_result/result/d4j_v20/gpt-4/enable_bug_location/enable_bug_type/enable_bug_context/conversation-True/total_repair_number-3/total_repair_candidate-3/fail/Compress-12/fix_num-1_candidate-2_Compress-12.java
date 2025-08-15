public TarArchiveEntry getNextTarEntry() throws IOException {
    if (hasHitEOF) {
        return null;
    }

    if (currEntry != null) {
        long numToSkip = entrySize - entryOffset;

        while (numToSkip > 0) {
            long skipped = skip(numToSkip);
            if (skipped <= 0) {
                throw new RuntimeException("failed to skip current tar entry");
            }
            numToSkip -= skipped;
        }

        readBuf = null;
    }

    byte[] headerBuf = getRecord();

    if (headerBuf == null) {
        currEntry = null;
        return null;
    }

    // Input validation: ensure headerBuf is expected size and format
    if (headerBuf.length != buffer.getRecordSize()) {
        throw new IOException("Invalid header block size: " + headerBuf.length);
    }

    currEntry = new TarArchiveEntry(headerBuf);
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length = 0;
        long bytesRead = 0;
        long longNameSize = entrySize; // size of the long name from the entry

        // read only as many bytes as indicated by entrySize, to avoid over-read
        while (bytesRead < longNameSize) {
            int bytesToRead = (int) Math.min(buf.length, longNameSize - bytesRead);
            length = read(buf, 0, bytesToRead);
            if (length < 0) {
                // premature EOF while reading long name
                throw new IOException("Unexpected EOF while reading long name");
            }
            longName.append(new String(buf, 0, length, "UTF-8"));
            bytesRead += length;
        }

        // After reading the long name, consume any remaining bytes to reach the block size
        long numToSkip = entrySize - bytesRead;
        while (numToSkip > 0) {
            long skipped = skip(numToSkip);
            if (skipped <= 0) {
                throw new IOException("Failed to skip padding after long name");
            }
            numToSkip -= skipped;
        }

        // move to next header entry
        TarArchiveEntry next = getNextTarEntry();
        if (next == null) {
            // Malformed tar file - long entry name not followed by entry
            return null;
        }

        // remove trailing null terminator if present
        int len = longName.length();
        if (len > 0 && longName.charAt(len - 1) == '\0') {
            longName.deleteCharAt(len - 1);
        }
        next.setName(longName.toString());
        currEntry = next;
        entrySize = currEntry.getSize();
        entryOffset = 0;
    }

    if (currEntry.isPaxHeader()) { // Process Pax headers
        paxHeaders();
    }

    if (currEntry.isGNUSparse()) { // Process sparse files
        readGNUSparse();
    }

    // Update entrySize again in case it changed due to pax headers or sparse info
    entrySize = currEntry.getSize();
    return currEntry;
}
