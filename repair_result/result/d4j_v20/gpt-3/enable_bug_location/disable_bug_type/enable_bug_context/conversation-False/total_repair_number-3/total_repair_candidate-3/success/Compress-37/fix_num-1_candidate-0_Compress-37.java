Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        while((ch = i.read()) != -1) {
            if (ch >= '0' && ch <= '9') {
                len *= 10;
                len += ch - '0';
                read++;
            } else if (ch == ' ') {
                read++;
                // Get keyword
                final ByteArrayOutputStream coll = new ByteArrayOutputStream();
                while((ch = i.read()) != -1) {
                    read++;
                    if (ch == '='){ // end of keyword
                        final String keyword = coll.toString(CharsetNames.UTF_8);
                        // Get rest of entry
                        final int restLen = len - read;
                        if (restLen < 0) {
                            // Malformed header with length smaller than keyword length
                            throw new IOException("Invalid Pax header length: " + len);
                        }
                        if (restLen == 1) { // only NL
                            headers.remove(keyword);
                        } else if (restLen > 1) {
                            final byte[] rest = new byte[restLen];
                            final int got = IOUtils.readFully(i, rest);
                            if (got != restLen) {
                                throw new IOException("Failed to read Paxheader. Expected "
                                                      + restLen + " bytes, read " + got);
                            }
                            // Drop trailing NL
                            final String value = new String(rest, 0,
                                                          restLen - 1, CharsetNames.UTF_8);
                            headers.put(keyword, value);
                        }
                        break;
                    }
                    coll.write((byte) ch);
                }
                break; // Processed single header
            } else if (ch == '\n') {
                // blank line, ignore and restart
                break; // break inner length-reading loop to restart
            } else {
                // Unexpected character, ignore until next line
                while (ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                break; // break inner length-reading loop to restart
            }
        }
        if (ch == -1){ // EOF
            break;
        }
    }
    return headers;
}
