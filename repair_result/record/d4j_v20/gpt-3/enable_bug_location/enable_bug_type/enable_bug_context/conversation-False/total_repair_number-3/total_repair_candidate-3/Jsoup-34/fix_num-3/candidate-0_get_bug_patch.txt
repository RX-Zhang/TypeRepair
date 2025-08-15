int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    if (seq == null || seq.length() == 0 || pos >= length) return -1;
    char startChar = seq.charAt(0);
    int seqLen = seq.length();
    int maxOffset = length - seqLen;
    for (int offset = pos; offset <= maxOffset; offset++) {
        // scan to first instance of startChar:
        if (input[offset] != startChar) {
            while (++offset <= maxOffset && input[offset] != startChar);
        }
        if (offset > maxOffset) break;

        int i = offset + 1;
        int last = offset + seqLen;
        int j = 1;
        while (i < last && input[i] == seq.charAt(j)) {
            i++;
            j++;
        }
        if (i == last) // found full sequence
            return offset - pos;
    }
    return -1;
}
