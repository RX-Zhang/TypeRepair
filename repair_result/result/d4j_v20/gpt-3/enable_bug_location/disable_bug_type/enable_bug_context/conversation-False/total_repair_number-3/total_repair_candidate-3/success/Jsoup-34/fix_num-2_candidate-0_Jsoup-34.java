int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    int max = length - seqLength + 1;
    for (int offset = pos; offset < max; offset++) {
        // scan to first instance of startChar:
        if (startChar != input[offset]) {
            while (++offset < max && startChar != input[offset]);
            if (offset >= max)
                break;
        }
        int i = offset + 1;
        int last = offset + seqLength;
        int j = 1;
        while (i < last && seq.charAt(j) == input[i]) {
            i++;
            j++;
        }
        if (i == last) // found full sequence
            return offset - pos;
    }
    return -1;
}
