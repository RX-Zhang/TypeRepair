Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        boolean gotLength = false;
        while((ch = i.read()) != -1) {
            read++;
            if (ch == ' '){
                gotLength = true;
                break;
            }
            if (ch < '0' || ch > '9') {
                // Invalid length format, skip this line or break
                // To prevent infinite loop or parse errors, break parsing
                ch = -1;
                break;
            }
            len *= 10;
            len += ch - '0';
        }

        if (!gotLength) {
            // EOF or invalid line
            break;
        }

        // len includes length of entire record including newline
        // read already counts characters read (length digits + 1 space)
        final int restLen = len - read;
        if (restLen < 0) {
            // Invalid length, prevent negative array size
            throw new IOException("Invalid Pax header length: " + len);
        }

        // Get keyword
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        int keywordEndPos = -1;
        int valRead = 0;
        while (valRead < restLen && (ch = i.read()) != -1) {
            valRead++;
            if (ch == '=') {
                keywordEndPos = valRead -1; // position of '=' relative to keyword bytes
                break;
            }
            coll.write((byte) ch);
        }

        if (keywordEndPos == -1) {
            // '=' not found in restLen bytes, malformed header
            // skip restLen - valRead bytes to continue safely
            int toSkip = restLen - valRead;
            while (toSkip > 0) {
                long skipped = i.skip(toSkip);
                if (skipped <= 0) {
                    throw new IOException("Failed to skip malformed Pax header");
                }
                toSkip -= skipped;
            }
            continue;
        }

        final String keyword = coll.toString(CharsetNames.UTF_8);

        // Now read value: restLen - valRead bytes remain.
        final int valueLen = restLen - valRead;
        if (valueLen < 0) {
            throw new IOException("Invalid value length in Pax header");
        }
        if (valueLen == 1) {
            // only newline, remove header
            headers.remove(keyword);
            // consume the trailing NL
            if (i.read() != '\n') {
                throw new IOException("Expected newline at end of Pax header");
            }
        } else {
            final byte[] rest = new byte[valueLen];
            final int got = IOUtils.readFully(i, rest);
            if (got != valueLen) {
                throw new IOException("Failed to read Paxheader. Expected "
                                      + valueLen + " bytes, read " + got);
            }
            // Drop trailing NL
            if (rest[valueLen - 1] != '\n') {
                throw new IOException("Pax header value not terminated with newline");
            }
            final String value = new String(rest, 0, valueLen - 1, CharsetNames.UTF_8);
            headers.put(keyword, value);
        }
    }
    return headers;
}
