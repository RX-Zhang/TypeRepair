Map<String, String> parsePaxHeaders(final InputStream i) throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;
        // Parse length field
        while ((ch = i.read()) != -1) {
            if (ch == '\n') {
                // Handle blank lines gracefully
                if (read == 0) {
                    continue;
                }
                break;
            }
            if (ch < '0' || ch > '9') {
                // Invalid length field character, skip line
                while ((ch = i.read()) != -1 && ch != '\n') {
                    // skip rest of line
                }
                len = 0;
                read = 0;
                break;
            }
            len *= 10;
            len += ch - '0';
            read++;
        }
        if (ch == -1) { // EOF
            break;
        }
        if (len == 0) {
            continue;
        }
        // Now len is total length of the record including length field itself
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        int toRead = len - read;
        while (toRead > 0) {
            ch = i.read();
            if (ch == -1) {
                throw new EOFException("Unexpected EOF in PAX header");
            }
            coll.write(ch);
            toRead--;
        }
        String record = coll.toString(CharsetNames.UTF_8);
        if (record.endsWith("\n")) {
            record = record.substring(0, record.length() - 1);
        }
        int equalsIndex = record.indexOf('=');
        if (equalsIndex == -1) {
            // invalid record, skip
            continue;
        }
        String keyword = record.substring(0, equalsIndex);
        String value = record.substring(equalsIndex + 1);
        if (value.isEmpty()) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
    return headers;
}
