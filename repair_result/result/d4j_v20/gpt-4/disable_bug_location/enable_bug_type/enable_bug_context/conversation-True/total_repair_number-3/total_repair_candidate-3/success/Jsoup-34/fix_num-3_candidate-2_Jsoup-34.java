int nextIndexOf(CharSequence seq) {
    if (seq == null || seq.length() == 0) return -1;
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    for (int offset = pos; offset <= length - seqLength; offset++) {
        if (input[offset] == startChar) {
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
    }
    return -1;
}
