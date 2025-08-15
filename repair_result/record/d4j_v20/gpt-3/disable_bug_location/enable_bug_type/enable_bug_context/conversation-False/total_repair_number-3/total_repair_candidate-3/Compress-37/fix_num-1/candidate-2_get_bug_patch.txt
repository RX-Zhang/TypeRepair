Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;

        // Skip any blank lines or extra newlines before a length line
        // read first char and if it's newline, continue
        while (true) {
            ch = i.read();
            if (ch == -1) {
                return headers; // EOF
            }
            if (ch == '\n' || ch == '\r') {
                // skip blank lines (carriage returns or line feeds)
                continue;
            }
            if (ch >= '0' && ch <= '9') {
                len = ch - '0';
                read = 1;
                break;
            }
            // If not digit or newline, malformed header, skip or break
            // For robustness, break here to attempt parse further
            break;
        }
        if (ch == -1) { // EOF
            break;
        }

        // Read the rest of the length digits
        while((ch = i.read()) != -1) {
            if (ch == ' ') {
                read++;
                break;
            }
            if (ch < '0' || ch > '9') {
                // Unexpected char in length field, break to avoid infinite loops
                break;
            }
            len = len * 10 + (ch - '0');
            read++;
        }
        if (ch == -1) { // EOF
            break;
        }

        // If length < read, then invalid header, break
        if (len < read) {
            // Possibly blank or malformed, break or continue to next
            break;
        }

        // Now read the keyword part until '='
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        boolean foundEqual = false;
        while((ch = i.read()) != -1) {
            read++;
            if (ch == '=') { // end of keyword
                foundEqual = true;
                break;
            }
            coll.write((byte) ch);
        }
        if (!foundEqual || ch == -1) {
            break; // malformed header or EOF
        }

        final String keyword = coll.toString(CharsetNames.UTF_8);
        // Calculate rest of entry length
        final int restLen = len - read;
        if (restLen < 0) {
            // malformed header length smaller than data read; skip
            break;
        }
        if (restLen == 1) { // only NL
            headers.remove(keyword);
            // Consume the single newline character
            int nl = i.read();
            if (nl != '\n' && nl != -1) {
                // Could be \r\n potentially, attempt to consume \n if \r encountered
                if (nl == '\r') {
                    int nl2 = i.read();
                    if (nl2 != '\n' && nl2 != -1) {
                        // Invalid, ignore
                    }
                }
            }
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
            // Drop trailing NL (could be \n or \r\n)
            int strLen = restLen - 1;
            if (strLen > 0 && rest[strLen - 1] == '\r') {
                strLen--;
            }
            final String value = new String(rest, 0, strLen, CharsetNames.UTF_8);
            headers.put(keyword, value);
        }
    }
    return headers;
}
