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
                // Blank line, skip it and continue reading next length
                len = 0;
                read = 0;
                continue;
            }
            if (ch == ' ') {
                break;
            }
            if (ch < '0' || ch > '9') {
                throw new IOException("Invalid length field in pax header");
            }
            len = len * 10 + (ch - '0');
        }
        if (ch == -1) { // EOF
            break;
        }
        if (len <= 0) {
            // Defensive: skip invalid or zero length entry
            // Consume rest of this line, if any
            for (int iSkip = 0; iSkip < len - 1; iSkip++) {
                if (i.read() == -1) {
                    break;
                }
            }
            continue;
        }

        int readKeywordAndEquals = 0;
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        while((ch = i.read()) != -1) {
            readKeywordAndEquals++;
            if (ch == '='){ // end of keyword
                final String keyword = coll.toString(CharsetNames.UTF_8);
                // Calculate how many bytes remain to read for the value, including trailing newline
                int restLen = len - read - readKeywordAndEquals;
                if (restLen < 0) {
                    throw new IOException("Paxheader entry length too short for keyword and value");
                }
                if (restLen == 0) {
                    // No value and no trailing newline, remove keyword
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
                    // Drop trailing NL if present
                    int valueLen = restLen;
                    if (restLen > 0 && rest[restLen - 1] == '\n') {
                        valueLen = restLen - 1;
                    }
                    final String value = new String(rest, 0,
                                                  valueLen, CharsetNames.UTF_8);
                    headers.put(keyword, value);
                }
                break;
            }
            coll.write((byte) ch);
        }
        if (ch == -1) {
            // EOF after reading partial header - exit loop
            break;
        }
    }
    return headers;
}
