int nextIndexOf(CharSequence seq) {
    char startChar = seq.charAt(0);
    int seqLen = seq.length();
    for (int offset = pos; offset <= length - seqLen; offset++) {
        if (input[offset] != startChar)
            continue;
        boolean found = true;
        for (int j = 1; j < seqLen; j++) {
            if (input[offset + j] != seq.charAt(j)) {
                found = false;
                break;
            }
        }
        if (found)
            return offset - pos;
    }
    return -1;
}
