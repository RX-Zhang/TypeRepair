Map<String, String> parsePaxHeaders(final InputStream i)
    throws IOException {
    final Map<String, String> headers = new HashMap<String, String>(globalPaxHeaders);
    // Format is "length keyword=value\n";
    while(true){ // get length
        int ch;
        int len = 0;
        int read = 0;
        // Read length part, which is decimal digits until space
        while((ch = i.read()) != -1) {
            if (ch == ' ') {
                break; // end of length
            }
            if (ch < '0' || ch > '9') {
                throw new IOException("Invalid length character '" + (char) ch + "'");
            }
            len = len * 10 + (ch - '0');
        }
        if (ch == -1) { // EOF before length finished
            break;
        }
        if (len <= 0) {
            break;
        }
        // Read the rest of the record, length bytes including length digits, space, keyword=value and terminating \n
        byte[] record = new byte[len];
        record[0] = (byte) ' '; // already read space, so we simulate it here
        int got = IOUtils.readFully(i, record, 1, len - 1);
        if (got != len - 1) {
            throw new IOException("Failed to read Paxheader. Expected " + (len - 1) + " bytes, read " + got);
        }
        // record is like: " keyword=value\n"
        // find '='
        int equalsIndex = -1;
        for (int idx = 1; idx < record.length; idx++) {
            if (record[idx] == '=') {
                equalsIndex = idx;
                break;
            }
        }
        if (equalsIndex == -1) {
            // Malformed record, skip it
            continue;
        }
        // Extract keyword between 1 and equalsIndex-1
        String keyword = new String(record, 1, equalsIndex -1, CharsetNames.UTF_8);
        // The value is between equalsIndex+1 and last byte minus 1 (to remove trailing \n)
        int valueLen = len - (equalsIndex + 2);
        if (valueLen < 0) {
            // Malformed record, skip it
            continue;
        }
        if (valueLen == 0) {
            // value is empty string, remove if exists
            headers.remove(keyword);
        } else {
            String value = new String(record, equalsIndex + 1, valueLen, CharsetNames.UTF_8);
            headers.put(keyword, value);
        }
    }
    return headers;
}
