int nextIndexOf(CharSequence seq) {
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    int lastIndex = length - seqLength;
    for (int offset = pos; offset <= lastIndex; offset++) {
        // scan to first instance of startChar
        if (input[offset] != startChar) {
            continue;
        }
        // check if the following characters match the sequence
        int i = 1;
        while (i < seqLength && input[offset + i] == seq.charAt(i)) {
            i++;
        }
        if (i == seqLength) { // found full sequence
            return offset - pos;
        }
    }
    return -1;
}
