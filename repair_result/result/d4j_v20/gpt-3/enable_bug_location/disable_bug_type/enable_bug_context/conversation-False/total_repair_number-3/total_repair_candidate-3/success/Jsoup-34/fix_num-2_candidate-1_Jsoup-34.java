int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    int lastIndex = length - seqLength + 1;
    for (int offset = pos; offset < lastIndex; offset++) {
        // scan to first instance of startChar:
        if (startChar != input[offset]) {
            while (++offset < lastIndex && startChar != input[offset]);
        }
        if (offset >= lastIndex)
            break;
        int i = offset + 1;
        int j = 1;
        while (j < seqLength && seq.charAt(j) == input[i]) {
            i++;
            j++;
        }
        if (j == seqLength) // found full sequence
            return offset - pos;
    }
    return -1;
}
