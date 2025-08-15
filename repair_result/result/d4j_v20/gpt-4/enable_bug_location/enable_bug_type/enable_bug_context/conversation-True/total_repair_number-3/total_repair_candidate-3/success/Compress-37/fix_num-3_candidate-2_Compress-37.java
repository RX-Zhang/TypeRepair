Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        while((ch = i.read()) != -1) {
            read++;
            if (ch == ' '){
                // Get keyword
                final ByteArrayOutputStream coll = new ByteArrayOutputStream();
                while((ch = i.read()) != -1) {
                    read++;
                    if (ch == '='){ // end of keyword
                        final String keyword = coll.toString(CharsetNames.UTF_8);
                        // Get rest of entry
                        final int restLen = len - read;
                        if (restLen < 0) {
                            throw new IOException("PaxHeader length too short: " + len);
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
                        break;
                    }
                    coll.write((byte) ch);
                }
                break; // Processed single header
            }
            if (ch < '0' || ch > '9') {
                // Invalid character in length field, skip rest of line
                while (ch != '\n' && ch != -1) {
                    ch = i.read();
                }
                len = 0;
                read = 0;
                continue;
            }
            len *= 10;
            len += ch - '0';
        }
        if (ch == -1){ // EOF
            break;
        }
        // If we have read the header, but did not process it due to breaks, continue
        if (len == 0) {
            break;
        }
    }
    return headers;
}
