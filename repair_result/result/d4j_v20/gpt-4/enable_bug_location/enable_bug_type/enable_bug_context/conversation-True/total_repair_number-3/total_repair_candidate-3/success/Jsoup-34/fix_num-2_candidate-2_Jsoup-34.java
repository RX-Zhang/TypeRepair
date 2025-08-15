int nextIndexOf(CharSequence seq) {
    if (seq.length() == 0)
        return 0;

    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    int max = length - seqLength; // last index to start matching

    for (int offset = pos; offset <= max; offset++) {
        if (input[offset] == startChar) {
            int i = offset + 1;
            int j = 1;
            while (j < seqLength && input[i] == seq.charAt(j)) {
                i++;
                j++;
            }
            if (j == seqLength)
                return offset - pos;
        }
    }
    return -1;
}
