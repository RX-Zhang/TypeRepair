Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        // Read the length field which is decimal digits followed by space
        while((ch = i.read()) != -1) {
            read++;
            if (ch == ' '){
                break;
            }
            // Check that ch is a digit to avoid malformed input causing negative length
            if (ch < '0' || ch > '9') {
                // Malformed header line, skip this header
                // Read until next newline
                while (ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                len = 0;
                break;
            }
            len *= 10;
            len += ch - '0';
        }
        if (ch == -1) { // EOF reached
            break;
        }
        if (len == 0) { // Blank or malformed line, skip reading keyword and value
            // Just skip this line and continue
            continue;
        }
        // Get keyword
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        while((ch = i.read()) != -1) {
            read++;
            if (ch == '='){ // end of keyword
                final String keyword = coll.toString(CharsetNames.UTF_8);
                // Get rest of entry
                final int restLen = len - read;
                if (restLen < 0) {
                    throw new IOException("Invalid Pax header length: " + len + " shorter than expected bytes read " + read);
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
                    // Drop trailing NL if present
                    int valueLen = restLen;
                    if (restLen > 0 && rest[restLen - 1] == '\n') {
                        valueLen = restLen - 1;
                    }
                    final String value = new String(rest, 0,
                                              valueLen, CharsetNames.UTF_8);
                    headers.put(keyword, value);
                }
                break;
            }
            coll.write((byte) ch);
        }
        // In case '=' was not found, avoid infinite loop
        if (ch == -1) {
            break;
        }
    }
    return headers;
}
