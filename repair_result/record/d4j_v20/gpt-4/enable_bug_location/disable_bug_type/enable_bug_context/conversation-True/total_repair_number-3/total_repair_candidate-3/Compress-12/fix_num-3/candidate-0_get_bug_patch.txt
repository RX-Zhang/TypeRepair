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

    currEntry = new TarArchiveEntry(headerBuf);
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length = 0;
        // Read the long name fully, but respecting entry size
        long numToRead = entrySize;
        while (numToRead > 0) {
            int toRead = (int) Math.min(buf.length, numToRead);
            length = read(buf, 0, toRead);
            if (length < 0) {
                break;
            }
            longName.append(new String(buf, 0, length, "UTF-8"));
            numToRead -= length;
        }
        // Move to next entry after reading long name
        currEntry = (TarArchiveEntry) getNextEntry();
        if (currEntry == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            return null;
        }
        // remove trailing null terminator
        int lastCharIdx = longName.length() - 1;
        if (lastCharIdx >= 0 && longName.charAt(lastCharIdx) == '\0') {
            longName.deleteCharAt(lastCharIdx);
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
