public TarArchiveEntry getNextTarEntry() throws IOException {
    if (hasHitEOF) {
        return null;
    }

    if (currEntry != null) {
        long numToSkip = entrySize - entryOffset;

        while (numToSkip > 0) {
            long skipped = skip(numToSkip);
            if (skipped <= 0) {
                throw new IOException("failed to skip current tar entry");
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

    try {
        currEntry = new TarArchiveEntry(headerBuf);
    } catch (IllegalArgumentException e) {
        // Wrap IllegalArgumentException in IOException to conform to method signature
        throw new IOException("Invalid tar header encountered", e);
    }
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length = 0;
        while ((length = read(buf, 0, buf.length)) >= 0) {
            // Validate bytes before appending to avoid invalid chars
            String chunk = new String(buf, 0, length, "UTF-8");
            longName.append(chunk);
        }
        getNextEntry();
        if (currEntry == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            return null;
        }
        // remove trailing null terminator or any null characters at the end
        int len = longName.length();
        while (len > 0 && longName.charAt(len - 1) == '\0') {
            longName.deleteCharAt(len - 1);
            len--;
        }
        currEntry.setName(longName.toString());
    }

    if (currEntry.isPaxHeader()) { // Process Pax headers
        paxHeaders();
    }

    if (currEntry.isGNUSparse()) { // Process sparse files
        readGNUSparse();
    }

    // If the size of the next element in the archive has changed
    // due to a new size being reported in the posix header
    // information, we update entrySize here so that it contains
    // the correct value.
    entrySize = currEntry.getSize();
    return currEntry;
}
