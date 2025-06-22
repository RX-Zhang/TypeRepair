Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;

        // Read the length field until a space or EOF
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == ' ') {
                break; // length field ended
            }
            if (ch < '0' || ch > '9') {
                throw new IOException("Malformed pax header length field");
            }
            len = len * 10 + (ch - '0');
        }

        if (ch == -1) {
            break; // EOF reached before next entry
        }

        if (len <= 0) {
            // Invalid length, skip the malformed entry or stop processing
            break;
        }

        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        int keywordLen = 0;
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == '=') { // end of keyword
                break;
            }
            coll.write((byte) ch);
            keywordLen++;
            if (keywordLen > len) {
                // keyword cannot be longer than total length field
                throw new IOException("Malformed pax header: keyword too long");
            }
        }

        if (ch == -1) {
            // EOF before '=' found
            break;
        }

        final String keyword = coll.toString(CharsetNames.UTF_8);
        final int restLen = len - read;
        if (restLen < 1) {
            // restLen should at least have the trailing newline
            throw new IOException("Malformed pax header length");
        }

        final byte[] rest = new byte[restLen];
        final int got = IOUtils.readFully(i, rest);
        if (got != restLen) {
            throw new IOException("Failed to read Paxheader. Expected "
                    + restLen + " bytes, read " + got);
        }
        // Drop trailing NL
        final String value = new String(rest, 0, restLen - 1, CharsetNames.UTF_8);

        if (restLen == 1) { // only NL, means delete header entry if exists
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
    return headers;
}
