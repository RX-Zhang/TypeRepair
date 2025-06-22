int nextIndexOf(CharSequence seq) {
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    for (int offset = pos; offset <= length - seqLength; offset++) {
        if (input[offset] == startChar) {
            int i = 1;
            for (; i < seqLength; i++) {
                if (input[offset + i] != seq.charAt(i))
                    break;
            }
            if (i == seqLength)
                return offset - pos;
        }
    }
    return -1;
}
