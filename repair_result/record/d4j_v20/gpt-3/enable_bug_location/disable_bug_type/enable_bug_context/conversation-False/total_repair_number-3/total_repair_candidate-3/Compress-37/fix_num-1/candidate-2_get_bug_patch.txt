Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        // Read length (a decimal number)
        while((ch = i.read()) != -1) {
            if (ch == '\n') {
                // Blank line - ignore and continue outer loop
                len = 0;
                read = 0;
                continue;
            }
            if (ch == ' ') {
                read++;
                break; // length complete
            }
            if (ch < '0' || ch > '9') {
                // Invalid length format, skip until newline or EOF
                while (ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                len = 0;
                read = 0;
                break;
            }
            len = len * 10 + (ch - '0');
            read++;
        }
        if (ch == -1) { // EOF
            break;
        }
        if (len == 0) {
            // No length found, continue to next header
            continue;
        }
        // Now read keyword=value\n of length len - length bytes already counted
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        int toRead = len - read; // number of bytes left in this record
        while(toRead > 0) {
            ch = i.read();
            if (ch == -1) {
                throw new IOException("Unexpected EOF in Pax header");
            }
            coll.write((byte)ch);
            toRead--;
        }
        // Convert to string (excluding trailing '\n')
        String entry = coll.toString(CharsetNames.UTF_8);
        if (entry.length() == 0 || entry.charAt(entry.length() -1) != '\n') {
            throw new IOException("Pax header entry does not end with newline");
        }
        entry = entry.substring(0, entry.length() - 1); // remove trailing newline
        int eqIdx = entry.indexOf('=');
        if (eqIdx == -1) {
            // No '=' in entry, ignore malformed record
            continue;
        }
        final String keyword = entry.substring(0, eqIdx);
        final String value = entry.substring(eqIdx + 1);
        if (value.isEmpty()) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
    return headers;
}
