Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;

        // read length as integer until space or EOF
        while((ch = i.read()) != -1) {
            if (ch == ' ') {
                break;
            }
            if (ch < '0' || ch > '9') {
                // Invalid character in length field, skip line or break processing
                // For robustness, skip; here we skip to next line
                // Read until newline
                while (ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                len = 0;
                break;
            }
            len *= 10;
            len += ch - '0';
        }

        if (ch == -1) { // EOF before header processed
            break;
        }

        if (len == 0) {
            // Blank line or invalid length, skip
            // consume until newline to move on
            while((ch = i.read()) != -1 && ch != '\n') {
                // no-op
            }
            if (ch == -1) {
                break;
            }
            continue;
        }

        read = 0;
        // read keyword until '=' or EOF
        final ByteArrayOutputStream coll = new ByteArrayOutputStream();
        while((ch = i.read()) != -1) {
            read++;
            if (ch == '=') { // end of keyword
                final String keyword = coll.toString(CharsetNames.UTF_8);
                final int restLen = len - read; // restLen includes '=' and value and NL

                if (restLen < 1) {
                    // Invalid header length, skip line
                    // consume until newline then continue
                    while(ch != -1 && ch != '\n') {
                        ch = i.read();
                    }
                    break;
                }
                if (restLen == 1) { // only NL after '=' means remove keyword
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
                    // Drop trailing NL
                    final String value = new String(rest, 0,
                                              restLen - 1, CharsetNames.UTF_8);
                    headers.put(keyword, value);
                }
                // consume trailing if any until newline just in case, but should be no bytes left
                break;
            }
            coll.write((byte) ch);
        }
        if (ch == -1) { // EOF inside keyword
            break;
        }
    }
    return headers;
}
