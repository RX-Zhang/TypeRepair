Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        boolean haveLength = false;
        // read length as decimal number until a space is found
        while((ch = i.read()) != -1) {
            if (ch == ' ') {
                haveLength = true;
                break;
            }
            if (ch < '0' || ch > '9') {
                // Invalid length character - skip until next newline or EOF
                while (ch != -1 && ch != '\n') {
                    ch = i.read();
                }
                len = -1; // invalid length flag
                break;
            }
            len = len * 10 + (ch - '0');
        }
        if (ch == -1) { // EOF
            break;
        }
        if (!haveLength || len <= 0) {
            // No valid length found, or length is non-positive; try to skip line and continue
            // Consume rest of line
            while (ch != -1 && ch != '\n') {
                ch = i.read();
            }
            if (ch == -1) {
                break;
            } else {
                continue;
            }
 }

        int toRead = len - 1; // length includes final \n, which we don't include in the
        byte[] buf = new byte[toRead];
        actuallyRead = IOUtils.readFully(i, buf);
        (actuallyRead != toRead) {
            throw new IOExceptionFailed to read PaxHeader. Expected " + toRead + bytes, read " + actuallyRead);
        }
        // data should be in the form: keyword=value\n
        equalsIndex = -1;
        for (int idx =0; idx < buf.length; idx++) {
            ifbuf[idx] == '=') {
                equalsIndex = idx;
                break;
            }
        }
        if (equalsIndex == -1) {
            // No equals sign found, malformed line, ignore and continue
            continue;
        }
        String keyword = new String(buf, 0, equalsIndex, CharsetNames.UTF_8);
        int valueLength = buf.length - equalsIndex - 1; // Exclude newline at end
        if (valueLength <= 0) {
            // If no value or just newline, remove the header if exists
            headers.remove(keyword);
        } else {
            String value = new String(buf, equalsIndex + 1, valueLength, CharsetNames.UTF_8);
            headers.put(keyword, value);
        }
    }
    return headers;
}
