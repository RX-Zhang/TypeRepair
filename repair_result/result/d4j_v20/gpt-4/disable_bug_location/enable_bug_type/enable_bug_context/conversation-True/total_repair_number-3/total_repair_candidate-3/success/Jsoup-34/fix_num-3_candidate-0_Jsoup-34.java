int nextIndexOf(CharSequence seq) {
    if (seq == null || seq.length() == 0) return -1;
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    int lastIndex = length - seqLength + 1;
    for (int offset = pos; offset < lastIndex; offset++) {
        if (input[offset] == startChar) {
            int i = offset + 1;
            int j = 1;
            while (j < seqLength && input[i] == seq.charAt(j)) {
                i++;
                j++;
            }
            if (j == seqLength) { // found full sequence
                return offset - pos;
            }
        }
    }
    return -1;
}
