Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;

        // Read the length field as a decimal number
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == ' ') {
                // Length parsing done
                break;
            }
            if (ch < '0' || ch > '9') {
                // Skip malformed entries (blank lines or invalid data)
                // Read until end of line and continue to next header.
                while (ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                len = 0;
                break;
            }
            len = len * 10 + (ch - '0');
        }
        if (ch == -1) { // EOF
            break;
        }
        if (len <= 0) {
            // Defensive: malformed length or blank line, skip it and continue
            continue;
        }

        // Now read the rest of the header line (len - length digits and space read)
        // We'll read len- read bytes, since read includes the digits and space
        final int toRead = len - read;
        if (toRead <= 0) {
            // Malformed header, skip it
            while ((ch = i.read()) != -1 && ch != '\n') {
                // skip rest of line
            }
            continue;
        }

        final byte[] rest = new byte[toRead];
        final int got = IOUtils.readFully(i, rest);
        if (got != toRead) {
            throw new IOException("Failed to read Paxheader. Expected "
                                  + toRead + " bytes, read " + got);
        }
        if (rest[toRead - 1] != '\n') {
            throw new IOException("Paxheader line does not end with newline");
        }

        // Now parse "keyword=value\n"
        // Find the '=' position
        int eqPos = -1;
        for (int idx = 0; idx < toRead -1; idx++) {
            if (rest[idx] == '=') {
                eqPos = idx;
                break;
            }
        }
        if (eqPos == -1) {
            // Malformed header, skip it
            continue;
        }

        final String keyword = new String(rest, 0, eqPos, CharsetNames.UTF_8);
        final String value = new String(rest, eqPos + 1, toRead - eqPos - 2, CharsetNames.UTF_8); // exclude = and trailing \n

        if (value.isEmpty()) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
    return headers;
}
