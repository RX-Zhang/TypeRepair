Map<String, String> parsePaxHeaders(final InputStream i) throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;

        // Read length field until space or EOF
        while ((ch = i.read()) != -1) {
            if (ch == ' ') {
                break;
            }
            if (ch < '0' || ch > '9') {
                // Invalid length character - skip this entry or break parsing
                throw new IOException("Invalid pax header length");
            }
            len *= 10;
            len += ch - '0';
            read++;
        }
        if (ch == -1) { // EOF
            break;
        }
        if (len == 0) {
            // No length specified, possible blank lines or invalid header; skip
            continue;
        }
        // Read the rest of the record into a buffer
        byte[] buffer = new byte[len - read];
        int got = IOUtils.readFully(i, buffer);
        if (got != buffer.length) {
            throw new IOException("Failed to read PaxHeader. Expected "
                    + buffer.length + " bytes, read " + got);
        }
        String entry = new String(buffer, 0, buffer.length, CharsetNames.UTF_8);

        // The last character must be a newline; if not, invalid record
        if (!entry.endsWith("\n")) {
            throw new IOException("PaxHeader record does not end with newline");
        }

        // Remove trailing newline
        entry = entry.substring(0, entry.length() - 1);

        int equalsIndex = entry.indexOf('=');
        if (equalsIndex == -1) {
            throw new IOException("PaxHeader record does not contain '=': " + entry);
        }
        String keyword = entry.substring(0, equalsIndex);
        String value = entry.substring(equalsIndex + 1);

        if (value.isEmpty()) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
    return headers;
}
