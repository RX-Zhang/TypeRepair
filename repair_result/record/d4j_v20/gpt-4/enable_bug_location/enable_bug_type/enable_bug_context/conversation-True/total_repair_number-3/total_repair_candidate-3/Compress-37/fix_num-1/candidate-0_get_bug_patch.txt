Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;
        // Read length field (digits until space)
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == ' ') {
                break;
            }
            if (ch < '0' || ch > '9') {
                // invalid length field, skip line to avoid NegativeArraySizeException
                while ((ch = i.read()) != -1 && ch != '\n') {
                    // skip until newline
                }
                ch = -1;
                break;
            }
            len *= 10;
            len += ch - '0';
        }

        if (ch == -1) { // EOF or invalid header length
            break;
        }

        if (len < read) {
            // malformed entry: length less than bytes we've already read
            // skip the rest of this line and continue
            while ((ch = i.read()) != -1 && ch != '\n') {
                // skip to newline
            }
            continue;
        }

        // Read keyword
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == '=') { // end of keyword
                break;
            }
            coll.write((byte) ch);
        }

        if (ch == -1) {
            break; // EOF before '='
        }

        final String keyword = coll.toString(CharsetNames.UTF_8);
        final int restLen = len - read;
        if (restLen < 1) {
            // Malformed entry, skip
            while ((ch = i.read()) != -1 && ch != '\n') {
                // skip to newline
            }
            continue;
        }

        if (restLen == 1) { // only NL
            headers.remove(keyword);
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
    return headers;
}
