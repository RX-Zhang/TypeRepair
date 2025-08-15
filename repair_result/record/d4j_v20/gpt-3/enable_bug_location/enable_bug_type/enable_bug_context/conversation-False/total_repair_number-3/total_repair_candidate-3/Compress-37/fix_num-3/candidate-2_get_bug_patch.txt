Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        // Read length (number) at start of each header line
        while(true) {
            ch = i.read();
            if (ch == -1) { // EOF
                return headers;
            }
            if (ch == ' ') { // end of length field
                break;
            }
            if (ch < '0' || ch > '9') {
                // Invalid character in length field, skip line or throw
                throw new IOException("Invalid character in pax header length field: " + (char) ch);
            }
            len = len * 10 + (ch - '0');
            read++;
        }

        if (len <= 0) {
            // Blank line or invalid length, skip or exit loop
            // Defensive: consume rest of line if any and continue
            for (int skip = 0; skip < len - read; skip++) {
                if (i.read() == -1) {
                    return headers;
                }
            }
            continue;
        }

        // Now read the actual line of length len (including length field, space, keyword=value\n)
        // We already read length digits and space, so read the rest: (len - read - 1) bytes (since newline at end)
        int restLen = len - read - 1; // subtract length field digits + space and trailing newline
        if (restLen <= 0) {
            // No data, skip to next header
            continue;
        }

        byte[] rest = new byte[restLen];
        int got = IOUtils.readFully(i, rest);
        if (got != restLen) {
            throw new IOException("Failed to read PaxHeader. Expected " + restLen + " bytes, read " + got);
        }

        String line = new String(rest, CharsetNames.UTF_8);
        // line should be "keyword=value\n" (with the final newline included in restLen, now dropped)
        int equalsIndex = line.indexOf('=');
        if (equalsIndex == -1) {
            // Invalid header line - no '=' character, ignore or throw
            continue;
        }

        String keyword = line.substring(0, equalsIndex);
        String value = line.substring(equalsIndex + 1);
        // Remove trailing newline if present
        if (value.endsWith("\n")) {
            value = value.substring(0, value.length() - 1);
        }

        if (value.length() == 0) {
            // if value is empty, remove keyword
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
}
