Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        
        // Read length field (number before space)
        while(true) {
            ch = i.read();
            if (ch == -1) {
                return headers; // EOF reached cleanly
            }
            if (ch == ' ') {
                break; // length field ended
            }
            if (ch < '0' || ch > '9') {
                // invalid character in length field - possibly blank or corrupt line, skip line
                // consume until newline:
                while(ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                break; // go to next header line
            }
            len = len * 10 + (ch - '0');
        }

        if (len == 0) {
            // blank or invalid line, skip line and continue
            // consume until newline
            while(ch != -1 && ch != '\n') {
                ch = i.read();
            }
            continue;
        }

        read = 0;
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        // read keyword until '='
        while(true) {
            ch = i.read();
            if (ch == -1) {
                // EOF before end of header line
                return headers;
            }
            read++;
            if (ch == '=') {
                break;
            }
            coll.write((byte) ch);
        }

        final String keyword = coll.toString(CharsetNames.UTF_8);
        final int restLen = len - read;

        if (restLen < 1) {
            // Invalid pax header length, skip this header line
            // consume rest of line if any
            int toSkip = -restLen;
            while (toSkip > 0) {
                ch = i.read();
                if (ch == -1) {
                    return headers;
                }
                toSkip--;
            }
            continue;
        }

        if (restLen == 1) { // only NL character
            // Remove entry for this keyword
            headers.remove(keyword);
            // read and discard newline
            ch = i.read();
            if (ch == -1) {
                return headers;
            }
        } else {
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
        }
    }
}
