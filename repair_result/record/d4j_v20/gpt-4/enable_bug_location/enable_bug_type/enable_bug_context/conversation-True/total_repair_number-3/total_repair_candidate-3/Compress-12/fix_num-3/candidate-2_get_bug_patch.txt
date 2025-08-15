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

    currEntry = new TarArchiveEntry(headerBuf);
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length = 0;
        // Read only up to entrySize bytes to avoid reading beyond the long name data
        long bytesRead = 0;
        while (bytesRead < entrySize && (length = read(buf, 0, (int)Math.min(buf.length, entrySize - bytesRead))) > 0) {
            longName.append(new String(buf, 0, length, "UTF-8"));
            bytesRead += length;
        }

        // Move to the next entry (the actual entry)
        getNextTarEntry();

        if (currEntry == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            return null;
        }

        // remove trailing null terminator if present
        int lastCharIndex = longName.length() - 1;
        if (lastCharIndex >= 0 && longName.charAt(lastCharIndex) == '\0') {
            longName.deleteCharAt(lastCharIndex);
        }

        currEntry.setName(longName.toString());
    }

    if (currEntry.isPaxHeader()) { // Process Pax headers
        paxHeaders();
    }

    if (currEntry.isGNUSparse()) { // Process sparse files
        readGNUSparse();
    }

    // Update entrySize in case PAX headers changed it
    entrySize = currEntry.getSize();

    return currEntry;
}
