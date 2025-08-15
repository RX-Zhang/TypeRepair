Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        // read length prefix digits
        while((ch = i.read()) != -1) {
            if (ch == ' ') {
                read++; // count space
                break; // length field finished
            }
            if (ch < '0' || ch > '9') {
                // invalid length character encountered, terminate parsing
                throw new IOException("Invalid length character in pax header");
            }
            len = len * 10 + (ch - '0');
            read++;
        }

        if (ch == -1) { // EOF reached while reading length
            break;
        }

        // len includes the length digits, the space, the keyword=value and \n
        if (len <= 0) {
            // zero or negative length not allowed, exit parsing
            break;
        }

        final int toRead = len - read;
        if (toRead <= 0) {
            // malformed pax header line length
            break;
        }

        final byte[] lineBytes = new byte[toRead];
        final int got = IOUtils.readFully(i, lineBytes);
        if (got != toRead) {
            throw new IOException("Failed to read pax header line");
        }

        // lineBytes is keyword=value\n
        if (lineBytes[toRead - 1] != '\n') {
            throw new IOException("Pax header line does not end with newline");
        }

        final String line = new String(lineBytes, 0, toRead - 1, CharsetNames.UTF_8);

        final int equalsIndex = line.indexOf('=');
        if (equalsIndex == -1) {
            // Invalid pax header line without '='
            continue; // or throw exception
        }

        final String key = line.substring(0, equalsIndex);
        final String value = line.substring(equalsIndex + 1);

        if (value.isEmpty()) {
            headers.remove(key);
        } else {
            headers.put(key, value);
        }
    }
    return headers;
}
