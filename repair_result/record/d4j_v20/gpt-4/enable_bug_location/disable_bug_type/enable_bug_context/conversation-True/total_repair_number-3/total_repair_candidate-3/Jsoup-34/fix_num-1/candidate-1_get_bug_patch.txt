int nextIndexOf(CharSequence seq) {
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    int offset = pos;
    while (offset < length) {
        // scan to first instance of startChar:
        if (input[offset] != startChar) {
            offset++;
            continue;
        }
        // check if match entire seq starting at offset
        if (offset + seqLength > length) {
            // not enough room for full seq
            return -1;
        }
        boolean matched = true;
        for (int i = 1; i < seqLength; i++) {
            if (input[offset + i] != seq.charAt(i)) {
                matched = false;
                break;
            }
        }
        if (matched) {
            return offset - pos;
        }
        offset++;
    }
    return -1;
}
