Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int len = 0;
        int ch;
        while((ch = i.read()) != -1) {
            if (ch == '\n') {
                // blank line, restart reading length
                break;
            }
            if (ch == ' ') {
                // finished reading length
                break;
            }
            if (ch < '0' || ch > '9') {
                throw new IOException("PAX header does not start with a length");
            }
            len *= 10;
            len += ch - '0';
        }
        if (ch == -1) {
            break; // EOF
        }
        if (ch == '\n') {
            continue; // handle blank line
        }

        // now read the record of length (len - digitsLen) bytes
        // we have already consumed the digits and the space: read restLen bytes
        final int restLen = len - Integer.toString(len).length() - 1;
        if (restLen <= 0) {
            continue;
        }
        final byte[] rest = new byte[restLen];
        final int got = IOUtils.readFully(i, rest);
        if (got != restLen) {
            throw new IOException("Failed to read PaxHeader. Expected " + restLen + " bytes, got " + got);
        }
        // The last byte should be a '\n'
        if (rest[restLen - 1] != '\n') {
            throw new IOException("PaxHeader line does not end with LF");
        }

        final String entry = new String(rest, 0, restLen - 1, CharsetNames.UTF_8);
        final int eqIdx = entry.indexOf('=');
        if (eqIdx == -1) {
            throw new IOException("PaxHeader cannot parse the format");
        }
        final String keyword = entry.substring(0, eqIdx);
        final String value = entry.substring(eqIdx + 1);
        if (value.length() == 0) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
    return headers;
}
