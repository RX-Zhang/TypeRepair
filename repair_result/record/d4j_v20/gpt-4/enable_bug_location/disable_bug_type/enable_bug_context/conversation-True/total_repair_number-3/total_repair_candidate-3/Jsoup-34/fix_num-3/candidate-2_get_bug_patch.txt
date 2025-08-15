int nextIndexOf(CharSequence seq) {
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    outer:
    for (int offset = pos; offset <= length - seqLength; offset++) {
        if (input[offset] == startChar) {
            for (int i = 1; i < seqLength; i++) {
                if (input[offset + i] != seq.charAt(i)) {
                    continue outer;
                }
            }
            return offset - pos;
        }
    }
    return -1;
}
