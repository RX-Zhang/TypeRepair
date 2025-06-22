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

    if (hasHitEOF) {
        currEntry = null;
        return null;
    }

    try {
        currEntry = new TarArchiveEntry(headerBuf);
    } catch (IllegalArgumentException e) {
        throw new IOException("Invalid tar entry header", e);
    }
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length;
        while ((length = read(buf)) > 0) {
            longName.append(new String(buf, 0, length, "UTF-8"));
        }
        getNextEntry();
        if (currEntry == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            return null;
        }
        // remove trailing null terminator (if any)
        int lastCharIndex = longName.length() - 1;
        if (lastCharIndex >= 0 && longName.charAt(lastCharIndex) == '\0') {
            longName.deleteCharAt(lastCharIndex);
        }
        currEntry.setName(longName.toString());
    }

    if (currEntry.isPaxHeader()){ // Process Pax headers
        paxHeaders();
    }

    if (currEntry.isGNUSparse()){ // Process sparse files
        readGNUSparse();
    }

    // Update entrySize in case it changed due to headers (PAX, etc)
    entrySize = currEntry.getSize();
    return currEntry;
}
