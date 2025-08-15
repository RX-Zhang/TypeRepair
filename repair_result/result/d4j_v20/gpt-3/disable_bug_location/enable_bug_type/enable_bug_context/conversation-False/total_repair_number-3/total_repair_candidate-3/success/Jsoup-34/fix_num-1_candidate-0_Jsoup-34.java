int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    char startChar = seq.charAt(0);
    int seqLen = seq.length();
    int max = length - seqLen;
    for (int offset = pos; offset <= max; offset++) {
        // scan to first instance of startChar:
        if (input[offset] != startChar)
            continue;
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
