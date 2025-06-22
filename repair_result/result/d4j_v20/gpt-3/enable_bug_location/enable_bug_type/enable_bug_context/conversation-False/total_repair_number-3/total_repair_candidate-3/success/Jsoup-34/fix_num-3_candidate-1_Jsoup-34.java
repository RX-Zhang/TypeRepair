int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    char startChar = seq.charAt(0);
    for (int offset = pos; offset < length; offset++) {
        // scan to first instance of startChar:
        if (startChar != input[offset])
            while (++offset < length && startChar != input[offset]);
        if (offset >= length) {
            break;
        }
        int i = offset + 1;
        int last = offset + seq.length();
        if (last > length) {
            // sequence would extend past input end - no match
            break;
        }
        boolean found = true;
        for (int j = 1; j < seq.length(); j++, i++) {
            if (input[i] != seq.charAt(j)) {
                found = false;
                break;
            }
        }
        if (found) {
            return offset - pos;
        }
    }
    return -1;
}
