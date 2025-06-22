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

    // Validate header buffer size before constructing the entry
    if (headerBuf.length < 512) {
        throw new IOException("Invalid tar header: incomplete record");
    }

    currEntry = new TarArchiveEntry(headerBuf);
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length;
        while ((length = read(buf, 0, buf.length)) > 0) {
            // Filter out invalid characters and sanitize input by replacing invalid UTF-8 sequences
            String chunk = new String(buf, 0, length, "UTF-8");
            longName.append(chunk);
        }
        // Consume the next entry which is the actual entry with the long name
        TarArchiveEntry next = getNextTarEntry();
        if (next == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            currEntry = null;
            return null;
        }
        currEntry = next;

        // remove trailing null terminator(s)
        int lnLength = longName.length();
        while (lnLength > 0 && longName.charAt(lnLength - 1) == '\0') {
            longName.deleteCharAt(lnLength - 1);
            lnLength--;
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

    // If the size of the next element in the archive has changed
    // due to a new size being reported in the posix header
    // information, we update entrySize here so that it contains
    // the correct value.
    entrySize = currEntry.getSize();

    return currEntry;
}
