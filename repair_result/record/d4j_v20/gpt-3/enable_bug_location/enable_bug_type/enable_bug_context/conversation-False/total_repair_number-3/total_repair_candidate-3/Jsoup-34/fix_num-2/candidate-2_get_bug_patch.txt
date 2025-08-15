int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    if (seq.length() == 0) return -1; // nothing to find
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    for (int offset = pos; offset < length; offset++) {
        // scan to first instance of startChar:
        if (startChar != input[offset]) {
            while (++offset < length && startChar != input[offset]);
        }
        if (offset >= length) {
            break;
        }
        int i = offset + 1;
        int last = offset + seqLength;
        if (last <= length) {
            int j = 1;
            while (i < last && seq.charAt(j) == input[i]) {
                i++;
                j++;
            }
            if (i == last) // found full sequence
                return offset - pos;
        }
    }
    return -1;
}
