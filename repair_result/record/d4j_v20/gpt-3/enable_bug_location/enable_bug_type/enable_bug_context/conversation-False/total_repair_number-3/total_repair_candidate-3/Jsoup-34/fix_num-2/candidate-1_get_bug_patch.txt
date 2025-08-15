int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    if (seq == null || seq.length() == 0) return -1;
    char startChar = seq.charAt(0);
    int seqLen = seq.length();
    for (int offset = pos; offset < length; offset++) {
        // scan to first instance of startChar:
        if (startChar != input[offset]) {
            while (++offset < length && startChar != input[offset]);
        }
        if (offset >= length) break;  // prevent out of bounds

        int i = offset + 1;
        int last = offset + seqLen;
        if (last > length) {
            // Not enough chars remaining to match seq
            break;
        }

        int j = 1;
        boolean matched = true;
        while (j < seqLen) {
            if (input[i] != seq.charAt(j)) {
                matched = false;
                break;
            }
            i++;
            j++;
        }
        if (matched) {
            return offset - pos;
        }
    }
    return -1;
}
