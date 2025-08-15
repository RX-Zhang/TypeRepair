Map<String, String> parsePaxHeaders(final InputStream i) throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while (true) { // get length
        final byte[] lengthBuffer = new byte[12]; // max length for length field (arbitrary safe value)
        int index = 0;
        int ch;
        // Read length field until space or EOF
        while ((ch = i.read()) != -1) {
            if (ch == ' ') {
                break;
            }
            if (ch < '0' || ch > '9') {
                throw new IOException("Invalid PAX header length character: " + ch);
            }
            if (index >= lengthBuffer.length) {
                throw new IOException("PAX header length too long");
            }
            lengthBuffer[index++] = (byte) ch;
        }
        if (ch == -1) {
            break; // EOF
        }
        if (index == 0) {
            // Blank line or corrupt data, skip and continue
            continue;
        }
        final String lengthStr = new String(lengthBuffer, 0, index, CharsetNames.UTF_8);
        final int len;
        try {
            len = Integer.parseInt(lengthStr);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid PAX header length: " + lengthStr, e);
        }
        if (len == 0) {
            // Blank line or invalid length, skip
            continue;
        }
        final byte[] buf = new byte[len];
        buf[0] = (byte) ch; // include the space just read
        final int restLen = len - 1;
        int got = IOUtils.readFully(i, buf, 1, restLen);
        if (got != restLen) {
            throw new IOException("Failed to read Paxheader. Expected "
                    + restLen + " bytes, read " + got);
        }
        // The format: "length keyword=value\n"
        // Find '=' and newline
        int eqIdx = -1;
        for (int j = 0; j < buf.length; j++) {
            if (buf[j] == '=') {
                eqIdx = j;
                break;
            }
        }
        if (eqIdx == -1 || buf[len - 1] != '\n') {
            throw new IOException("Invalid PAX header format");
        }
        final String keyword = new String(buf, 0, eqIdx, CharsetNames.UTF_8);
        final String value = new String(buf, eqIdx + 1, len - eqIdx - 2, CharsetNames.UTF_8); // exclude newline
        if (value.length() == 0) {
            headers.remove(keyword);
        } else {
            headers.put(keyword, value);
        }
    }
    return headers;
}
