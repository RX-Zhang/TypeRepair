Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        int ch;
        int len = 0;
        int read = 0;

        // Read length digits
        boolean foundLength = false;
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == ' ') {
                foundLength = true;
                break;
            }
            if (ch < '0' || ch > '9') {
                // If unexpected character found (e.g. blank line),
                // skip to next line to avoid infinite loop or error
                // consume until newline
                while (ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                foundLength = false;
                break;
            }
            len = len * 10 + (ch - '0');
        }

        if (!foundLength) {
            if (ch == -1) {
                // EOF reached, exit loop
                break;
            }
            // blank or invalid line encountered, continue parsing next header
            continue;
        }

        // If length is 0 or negative, skip this header (invalid)
        if (len <= 0) {
            // consume rest of the line if any
            int toSkip = len - read; // usually negative or zero
            if (toSkip > 0) {
                long skipped = 0;
                while (skipped < toSkip) {
                    long curSkipped = i.skip(toSkip - skipped);
                    if (curSkipped <= 0) {
                        break; // cannot skip further
                    }
                    skipped += curSkipped;
                }
            }
            continue;
        }

        // Get keyword
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        while ((ch = i.read()) != -1) {
            read++;
            if (ch == '=') { // end of keyword
                final String keyword = coll.toString(CharsetNames.UTF_8);
                final int restLen = len - read;
                if (restLen == 1) { // only NL
                    headers.remove(keyword);
                } else if (restLen > 1) {
                    final byte[] rest = new byte[restLen];
                    final int got = IOUtils.readFully(i, rest);
                    if (got != restLen) {
                        throw new IOException("Failed to read "
                                + "Paxheader. Expected "
                                + restLen
                                + " bytes, read "
                                + got);
                    }
                    // Drop trailing NL
                    final String value = new String(rest, 0,
                            restLen - 1, CharsetNames.UTF_8);
                    headers.put(keyword, value);
                }
                // if restLen <= 0 means malformed entry, ignore robustly
                break;
            }
            coll.write((byte) ch);
        }
        // If EOF in the middle of a header, break too
        if (ch == -1) {
            break;
        }
    }
    return headers;
}
