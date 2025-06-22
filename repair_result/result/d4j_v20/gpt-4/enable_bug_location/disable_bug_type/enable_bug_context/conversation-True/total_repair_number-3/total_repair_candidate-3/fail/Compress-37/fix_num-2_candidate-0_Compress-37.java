Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";

    while (true) {
        int len = 0;
        int ch;

        // Read the length field
        while (true) {
            ch = i.read();
            if (ch == -1) {
                return headers; // EOF: end parsing
            }
            if (ch == ' ') { // End of length field
                break;
            }
            if (ch < '0' || ch > '9') {
                throw new IOException("Failed to parse length header: invalid length character " + (char) ch);
            }
            len = len * 10 + (ch - '0');
        }

        if (len <= 0) {
            // Malformed or blank line; skip and continue
            // Try to consume the rest of the line
            while ((ch = i.read()) != -1) {
                if (ch == '\n') {
                    break;
                }
            }
            if (ch == -1) {
                return headers;
            }
            continue;
        }

        final byte[] entryBuffer = new byte[len];
        int got = IOUtils.readFully(i, entryBuffer);
        if (got != len) {
            throw new IOException("Failed to read Paxheader. Expected " + len + " bytes, read " + got);
        }

        if (entryBuffer[len - 1] != '\n') {
            throw new IOException("Failed to parse pax header line: missing trailing newline");
        }

        // Find the '=' that separates the keyword and value
        int equalsIndex = -1;
        for (int idx = 0; idx < entryBuffer.length; idx++) {
            if (entryBuffer[idx] == '=') {
                equalsIndex = idx;
                break;
            }
        }
        if (equalsIndex == -1) {
            throw new IOException("Failed to parse pax header line: '=' not found");
        }

        // Keyword is from after the length field (which is before the space) till the '='
        // But in this case, the whole recordBuffer contains "keyword=value\n" after length
        // So the keyword is bytes from 0 to equalsIndex (exclusive)
        String keyword = new String(entryBuffer, 0, equalsIndex, CharsetNames.UTF_8);
        String value = new String(entryBuffer, equalsIndex + 1, len - equalsIndex - 2, CharsetNames.UTF_8); // exclude \n

        if (value.length() == 0) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
}
