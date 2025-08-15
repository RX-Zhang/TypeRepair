int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    if (seq.length() == 0) return 0;
    char startChar = seq.charAt(0);
    outer:
    for (int offset = pos; offset <= length - seq.length(); offset++) {
        // scan to first instance of startChar:
        if (input[offset] != startChar) continue;
        // check if full sequence matches
        for (int j = 1; j < seq.length(); j++) {
            if (input[offset + j] != seq.charAt(j)) {
                continue outer;
            }
        }
        return offset - pos;
    }
    return -1;
}
