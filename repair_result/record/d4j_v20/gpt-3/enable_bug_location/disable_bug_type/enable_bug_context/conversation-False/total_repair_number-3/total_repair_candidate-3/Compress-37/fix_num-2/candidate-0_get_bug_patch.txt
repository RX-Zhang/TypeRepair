Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        boolean gotLength = false;
        // Read length prefix
        while((ch = i.read()) != -1) {
            if (ch >= '0' && ch <= '9') {
                len = len * 10 + (ch - '0');
                read++;
            } else if (ch == ' ') {
                read++;
                gotLength = true;
                break;
            } else if (ch == '\n') {
                // blank line; ignore and restart length read
                len = 0;
                read = 0;
            } else {
                // Invalid character in length field, ignore line
                // Consume until newline then restart
                while(ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                len = 0;
                read = 0;
            }
        }
        if (ch == -1) { // EOF
            break;
        }
        if (!gotLength) {
            // No length read properly, exit to avoid infinite loop
            break;
        }

        // Read keyword and value
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        boolean keywordDone = false;
        while((ch = i.read()) != -1) {
            read++;
            if (ch == '=') { // end of keyword
                keywordDone = true;
                break;
            }
            coll.write((byte) ch);
        }
        if (!keywordDone || ch == -1) {
            // Couldn't read keyword properly, exit loop
            break;
        }
        final String keyword = coll.toString(CharsetNames.UTF_8);
        final int restLen = len - read;
        if (restLen < 0) {
            // Invalid length, skip line and continue
            // Consume until newline to resync
            while(ch != -1 && ch != '\n') {
                ch = i.read();
            }
            continue;
        }
        if (restLen == 1) { // only NL
            headers.remove(keyword);
            // Consume trailing NL
            i.read();
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
            // restLen == 0, no data after keyword, ignore line
            // Possibly blank or malformed line, no-op
        }
    }
    return headers;
}
