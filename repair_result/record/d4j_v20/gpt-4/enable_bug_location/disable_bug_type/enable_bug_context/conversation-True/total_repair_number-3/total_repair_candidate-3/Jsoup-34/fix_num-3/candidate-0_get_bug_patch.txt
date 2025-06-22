int nextIndexOf(CharSequence seq) {
    if (seq.length() == 0)
        return 0;

    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    int last = length - seqLength + 1;
    for (int offset = pos; offset < last; offset++) {
        if (input[offset] == startChar) {
            int i = offset + 1;
            int j = 1;
            // compare subsequence
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
