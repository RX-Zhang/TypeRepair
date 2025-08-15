Map<String, String> parsePaxHeaders(final InputStream i) throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    int ch;
    while(true){ // get length
        int len = 0;
        int read = 0;
        
        // Read length field as digits until space
        do {
            ch = i.read();
            if (ch == -1) {
                if (len == 0) {
                    // EOF and no length read; end loop
                    return headers;
                } else {
                    throw new IOException("Failed to read length from pax header");
                }
            }
            if (ch < '0' || ch > '9') {
                if (ch != ' ') {
                    throw new IOException("Malformed pax header length");
                }
                break;
            }
            len = len * 10 + (ch - '0');
        } while(true);

        if (len == 0) {
            // Possibly blank line or padding
            // read until '\n' if not already read
            do {
                ch = i.read();
                if (ch == -1 || ch == '\n') {
                    break;
                }
            } while(true);
            continue;
        }

        // Read the entire record into a buffer
        byte[] buff = new byte[len];
        buff[0] = (byte) ch; // first space byte already read above, overwrite it below
        int got = IOUtils.readFully(i, buff, 1, len - 1);
        if (got != len -1) {
            throw new IOException("Failed to read PaxHeader entry fully");
        }

        // The buffer now contains the full line: "length keyword=value\n"
        // Find the first space to confirm start of keyword
        // Note: The 'length' field is not part of buff. So the stored space here is from i.read() previously, so buff[0] is first char of keyword

        int idx = 0;
        // Skip length bytes manually read; buff[0] is the first byte after the space, i.e. first byte of keyword
        for (; idx < buff.length; idx++) {
            if (buff[idx] == '=') {
                break;
            }
        }
        if (idx == buff.length) {
            throw new IOException("Invalid pax header: no '=' found");
        }

        String keyword = new String(buff, 0, idx, CharsetNames.UTF_8);
        String value = new String(buff, idx + 1, buff.length - idx - 2, CharsetNames.UTF_8); // exclude trailing '\n'

        if (value.length() == 0) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
}
