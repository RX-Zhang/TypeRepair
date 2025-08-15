Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;

        // Read length prefix
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == ' ') {
                break; // found space, length complete
            }
            if (ch < '0' || ch > '9') {
                throw new IOException("Invalid length character in Pax header: " + (char) ch);
            }
            len *= 10;
            len += ch - '0';
        }
        if (ch == -1) { // EOF reached prematurely
            break;
        }
        if (len <= 0) {
            // Length must be positive, but some tar implementations may have blank lines
            // ignoring blank or invalid headers silently
            // so skip rest until next line
            for (; ch != -1 && ch != '\n'; ch = i.read()) {
                // consume the rest of the invalid line
            }
            continue;
        }

        // Read the entire entry according to length
        byte[] entry = new byte[len];
        int got = 0;
        while (got < len) {
            int n = i.read(entry, got, len - got);
            if (n == -1) {
                throw new IOException("Failed to read Pax header entry of length " + len);
            }
            got += n;
        }

        // entry should be of form "keyword=value\n"
        if (entry[len - 1] != '\n') {
            throw new IOException("Pax header entry does not end with LF");
        }

        // Find '=' separator
        int eqIdx = -1;
        for (int idx = 0; idx < len; idx++) {
            if (entry[idx] == '=') {
                eqIdx = idx;
                break;
            }
        }
        if (eqIdx == -1) {
            throw new IOException("Pax header entry missing '=': " + new String(entry, CharsetNames.UTF_8));
        }
        String keyword = new String(entry, 0, eqIdx, CharsetNames.UTF_8);
        String value = new String(entry, eqIdx + 1, len - eqIdx - 2, CharsetNames.UTF_8); // exclude trailing \n

        if (value.length() == 0) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
    return headers;
}
