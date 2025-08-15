Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;

        // Read the length field
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == ' ') {
                // Got length part, now parse the keyword and value
                final ByteArrayOutputStream coll = new ByteArrayOutputStream();
                while ((ch = i.read()) != -1) {
                    read++;
                    if (ch == '=') { // end of keyword
                        final String keyword = coll.toString(CharsetNames.UTF_8);
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
                            // restLen <= 0 means malformed or empty, ignore this header line
                        }
                        break;
                    }
                    coll.write((byte) ch);
                }
                break; // Processed single header
            }
            // Check for digit before multiplying len
            if (ch < '0' || ch > '9') {
                // Malformed length field, skip this line or break
                while ((ch = i.read()) != -1 && ch != '\n') {
                    // skip to end of line
                }
                break;
            }
            len *= 10;
            len += ch - '0';
        }
        if (ch == -1) { // EOF
            break;
        }
    }
    return headers;
}
