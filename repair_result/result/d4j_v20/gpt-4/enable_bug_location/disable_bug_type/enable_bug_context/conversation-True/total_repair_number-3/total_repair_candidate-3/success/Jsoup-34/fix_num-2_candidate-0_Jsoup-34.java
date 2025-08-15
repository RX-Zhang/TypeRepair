int nextIndexOf(CharSequence seq) {
    char startChar = seq.charAt(0);
    int offset = pos;
    int seqLength = seq.length();
    int lastIndex = length - seqLength + 1;
    if (seqLength == 0)
        return -1;

    while (offset < lastIndex) {
        if (input[offset] != startChar) {
            offset++;
            continue;
        }
        boolean matched = true;
        for (int i = 1; i < seqLength; i++) {
            if (input[offset + i] != seq.charAt(i)) {
                matched = false;
                break;
            }
        }
        if (matched)
            return offset - pos;
        offset++;
    }

    return -1;
}
