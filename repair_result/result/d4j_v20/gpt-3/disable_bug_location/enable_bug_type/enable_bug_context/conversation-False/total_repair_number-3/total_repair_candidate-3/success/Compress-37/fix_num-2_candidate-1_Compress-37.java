Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;

        // Read length field: a series of digits terminated by space.
        // Also handle blank lines or invalid lines safely.
        boolean lengthRead = false;
        while ((ch = i.read()) != -1) {
            if (ch == ' ') {
                lengthRead = true;
                read++;
                break;
            }
            if (ch < '0' || ch > '9') {
                // Invalid character in length field - possibly blank line or corrupted header
                // Skip to next line (read until newline) and continue outer while loop
                while (ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                if (ch == -1) {
                    return headers; // EOF
                }
                // Restart parsing next header
                len = 0;
                read = 0;
                lengthRead = false;
                // Continue outer while loop
                continue;
            }
            len = len * 10 + (ch - '0');
            read++;
        }
        if (ch == -1) { // EOF
            break;
        }
        if (!lengthRead) {
            // EOF reached before length read fully
            break;
        }
        if (len <= 0) {
            // Length 0 or negative does not make sense, skip line and continue
            // Skip rest of line
            int skipCh;
            do {
                skipCh = i.read();
            } while (skipCh != -1 && skipCh != '\n');
            if (skipCh == -1) {
                break;
            }
            continue;
        }

        // Now read until '=' to extract keyword
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == '=') { // end of keyword
                final String keyword = coll.toString(CharsetNames.UTF_8);
                // Calculate rest length
                final int restLen = len - read;
                if (restLen == 1) { // only NL
                    headers.remove(keyword);
                } else if (restLen > 1) {
                    final byte[] rest = new byte[restLen];
                    final int got = IOUtils.readFully(i, rest);
                    if (got != restLen) {
                        throw new IOException("Failed to read "
                                              + "Paxheader. Expected "
                                              + restLen
                                              + " bytes, read "
                                              + got);
                    }
                    // Drop trailing NL
                    final String value = new String(rest, 0,
                                              restLen - 1, CharsetNames.UTF_8);
                    headers.put(keyword, value);
                } else {
                    // restLen <= 0 means malformed header, skip
                    // try to consume until newline to recover
                    while (ch != -1 && ch != '\n') {
                        ch = i.read();
                    }
                }
                break;
            }
            coll.write((byte) ch);
        }
        if (ch == -1) { // EOF (unexpected)
            break;
        }
    }
    return headers;
}
