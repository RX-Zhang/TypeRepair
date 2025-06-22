Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        // Read the length field
        boolean lengthRead = false;
        while((ch = i.read()) != -1) {
            read++;
            if (ch == ' ') {
                lengthRead = true;
                break;
            }
            if (ch >= '0' && ch <= '9') {
                len = len * 10 + (ch - '0');
            } else {
                // Invalid length character; skip this line
                // consume until newline
                while (ch != -1 && ch != '\n') {
                    ch = i.read();
                    read++;
                }
                lengthRead = false;
                break;
            }
        }
        if (ch == -1) { // EOF
            break;
        }
        if (!lengthRead) {
            // Skip blank or malformed line and continue parsing next header
            continue;
        }
        // Check for valid length
        if (len <= 0) {
            // Malformed or empty line, skip and continue
            // consume rest of line (if any)
            while (ch != -1 && ch != '\n') {
                ch = i.read();
                read++;
            }
            continue;
        }
        // Get keyword
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        while((ch = i.read()) != -1) {
            read++;
            if (ch == '='){ // end of keyword
                final String keyword = coll.toString(CharsetNames.UTF_8);
                final int restLen = len - read;
                if (restLen < 0) {
                    throw new IOException("PaxHeader entry length too small: " + len + " for keyword: " + keyword);
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
                // consume trailing newline if not already consumed
                break;
            }
            coll.write((byte) ch);
        }
        // consume until newline if '=' not found (malformed)
        if (ch == -1) {
            break;
        }
    }
    return headers;
}
