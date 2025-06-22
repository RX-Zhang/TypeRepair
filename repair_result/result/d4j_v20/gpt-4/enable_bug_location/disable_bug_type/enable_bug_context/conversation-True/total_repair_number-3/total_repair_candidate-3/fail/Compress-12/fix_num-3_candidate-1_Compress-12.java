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

    currEntry = new TarArchiveEntry(headerBuf);
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length = 0;
        while ((length = read(buf, 0, buf.length)) > 0) {
            longName.append(new String(buf, 0, length, "ISO-8859-1"));
        }
        // Now, consume the next entry which should contain the real entry.
        TarArchiveEntry nextEntry = getNextTarEntry();
        if (nextEntry == null) {
            // Malformed tar file - long entry name not followed by entry
            return null;
        }
        currEntry = nextEntry;
        // remove trailing null terminator if present
        int lnLen = longName.length();
        if (lnLen > 0 && longName.charAt(lnLen - 1) == 0) {
            longName.deleteCharAt(lnLen - 1);
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

    // If the size of the entry has changed due to Pax headers or other adjustments,
    // update entrySize accordingly.
    entrySize = currEntry.getSize();
    return currEntry;
}
