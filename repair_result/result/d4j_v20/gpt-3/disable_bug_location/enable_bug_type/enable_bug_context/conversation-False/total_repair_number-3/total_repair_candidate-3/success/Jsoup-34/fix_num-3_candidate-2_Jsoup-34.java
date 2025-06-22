int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    outer:
    for (int offset = pos; offset <= length - seqLength; offset++) {
        // scan to first instance of startChar:
        if (input[offset] != startChar) {
            continue;
        }
        for (int i = 1; i < seqLength; i++) {
            if (input[offset + i] != seq.charAt(i)) {
                continue outer;
            }
        }
        return offset - pos;
    }
    return -1;
}
