Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        while((ch = i.read()) != -1) {
            if (ch == ' '){
                read++;
                // If len is zero here, skip this line (blank line)
                if (len == 0) {
                    // Read to the end of this line (consume remainder)
                    while ((ch = i.read()) != -1 && ch != '\n') {
                        // discard
                    }
                    break; // go to next header line
                }
                // Get keyword
                final ByteArrayOutputStream coll = new ByteArrayOutputStream();
                while((ch = i.read()) != -1) {
                    read++;
                    if (ch == '='){ // end of keyword
                        final String keyword = coll.toString(CharsetNames.UTF_8);
                        // Get rest of entry
                        final int restLen = len - read;
                        if (restLen <= 0) { // no content or blank line
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
                throw new IOException("Invalid length field in Pax header");
            }
            len *= 10;
            len += ch - '0';
            read++;
        }
        if (ch == -1){ // EOF
            break;
        }
    }
    return headers;
}
