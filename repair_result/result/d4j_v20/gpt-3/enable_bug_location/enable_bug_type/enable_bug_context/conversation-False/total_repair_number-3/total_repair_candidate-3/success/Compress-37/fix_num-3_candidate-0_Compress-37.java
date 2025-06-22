Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        // Read length prefix
        while((ch = i.read()) != -1) {
            if (ch == '\n') {
                // Blank line, skip and continue outer loop
                continue;
            }
            if (ch < '0' || ch > '9') {
                // Invalid character encountered before space; skip line or break
                // Let's skip this line by reading until next '\n'
                while(ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                if (ch == -1) {
                    break;
                }
                // After skipping, restart length parse
                len = 0;
                read = 0;
                continue;
            }
            break;
        }
        if (ch == -1) {
            break; // EOF
        }
        // First digit read:
        len = ch - '0';
        read = 1;
        // read the rest of the length digits until space
        while((ch = i.read()) != -1) {
            read++;
            if (ch == ' '){
                break; // end of length field
            }
            if (ch < '0' || ch > '9') {
                // Unexpected character in length; skip line
                while(ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                if (ch == -1) {
                    break;
                }
                // Restart outer loop to read length again
                len = 0;
                read = 0;
                continue;
            }
            len = len * 10 + (ch - '0');
        }
        if (ch == -1) {
            break; // EOF
        }

        // Now read keyword=value\n part
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        boolean foundEquals = false;
        while((ch = i.read()) != -1) {
            read++;
            if (ch == '=') { // end of keyword
                foundEquals = true;
                break;
            }
            coll.write((byte) ch);
        }
        if (!foundEquals || ch == -1) {
            break; // Malformed entry or EOF
        }

        final String keyword = coll.toString(CharsetNames.UTF_8);

        final int restLen = len - read;
        if (restLen < 0) {
            // Length is invalid for the data read, skip this header
            // Or break? We choose to break as it's malformed
            break;
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
