Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;

        // Read the length number
        while ((ch = i.read()) != -1) {
            if (ch == ' ') {
                break;
            }
            if (ch < '0' || ch > '9') {
                // Found a non-digit before space, skip this header line (likely blank or corrupt)
                while (ch != '\n' && ch != -1) {
                    ch = i.read();
                }
                len = 0;
                break;
            }
            len = len * 10 + (ch - '0');
            read++;
        }

        if (ch == -1 || len == 0) { // EOF or empty line
            break;
        }

        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        // Read keyword up to '='
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == '=') { // end of keyword
                final String keyword = coll.toString(CharsetNames.UTF_8);
                final int restLen = len - read;
                if (restLen == 1) { // only NL
                    headers.remove(keyword);
                    // consume the trailing newline
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
                    // Invalid length, skip
                    while (ch != '\n' && ch != -1) {
                        ch = i.read();
                    }
                }
                break;
            }
            coll.write((byte) ch);
        }

        // If EOF reached inside keyword reading
        if (ch == -1) {
            break;
        }
    }
    return headers;
}
