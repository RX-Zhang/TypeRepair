int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    if (seq.length() == 0) return 0; // empty sequence always matches

    char startChar = seq.charAt(0);
    int seqLength = seq.length();

    for (int offset = pos; offset <= length - seqLength; offset++) {
        // scan to first instance of startChar:
        if (input[offset] != startChar) {
            continue;
        }
        int i = offset + 1;
        int j = 1;
        while (j < seqLength && input[i] == seq.charAt(j)) {
            i++;
            j++;
        }
        if (j == seqLength) {
            return offset - pos;
        }
    }
    return -1;
}
