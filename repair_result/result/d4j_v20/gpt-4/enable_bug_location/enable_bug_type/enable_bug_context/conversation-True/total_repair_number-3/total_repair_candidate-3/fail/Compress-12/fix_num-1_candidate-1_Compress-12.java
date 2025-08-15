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

    if (hasHitEOF || headerBuf == null) {
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
        int length;
        while ((length = read(buf, 0, buf.length)) >= 0) {
            // Validate and sanitize input to ensure no invalid bytes
            for (int i = 0; i < length; i++) {
                if (buf[i] < 0) {
                    throw new IOException("Invalid byte detected in long name entry");
                }
            }
            longName.append(new String(buf, 0, length, "UTF-8"));
        }
        TarArchiveEntry nextEntry = getNextTarEntry();
        if (nextEntry == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            return null;
        }
        currEntry = nextEntry;
        // remove trailing null terminator
        if (longName.length() > 0 && longName.charAt(longName.length() - 1) == 0) {
            longName.deleteCharAt(longName.length() - 1);
        }
        currEntry.setName(longName.toString());
        entrySize = currEntry.getSize();
        entryOffset = 0;
    }

    if (currEntry.isPaxHeader()) { // Process Pax headers
        paxHeaders();
    }

    if (currEntry.isGNUSparse()) { // Process sparse files
        readGNUSparse();
    }

    // Update entrySize after processing potential headers
    entrySize = currEntry.getSize();
    return currEntry;
}
