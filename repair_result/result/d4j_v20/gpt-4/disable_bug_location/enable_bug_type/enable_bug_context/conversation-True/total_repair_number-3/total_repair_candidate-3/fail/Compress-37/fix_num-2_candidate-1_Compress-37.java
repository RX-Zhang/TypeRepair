Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;
        // Read length prefix digits
        while ((ch = i.read()) != -1) {
            if (ch == ' ') {
                break;
            }
            if (ch < '0' || ch > '9') {
                // Invalid character in length field; skip this header
                throw new IOException("Malformed Pax Header: invalid length field");
            }
            len = len * 10 + (ch - '0');
            read++;
        }
        if (ch == -1) { // EOF before space after length
            break;
        }
        if (len == 0) {
            // No length found or zero length, stop parsing
            break;
        }
        // Now read the "keyword=value\n"
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        int keywordEnd = -1;
        for (int iRead = 0; iRead < len - (read + 1); iRead++) {
            int b = i.read();
            if (b == -1) {
                throw new IOException("Unexpected EOF reading Pax header");
            }
            coll.write(b);
            if (keywordEnd == -1 && b == '=') {
                keywordEnd = coll.size() - 1;
            }
        }
        int trailingByte = i.read();
        if (trailingByte != '\n') {
            throw new IOException("Malformed Pax Header: missing trailing newline");
        }

        if (keywordEnd == -1) {
            throw new IOException("Malformed Pax Header: missing '=' in header");
        }

        final String keyword = new String(coll.toByteArray(), 0, keywordEnd, CharsetNames.UTF_8);
        final String value = new String(coll.toByteArray(), keywordEnd + 1, coll.size() - keywordEnd -1, CharsetNames.UTF_8);
        if (value.length() == 0) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
    return headers;
}
