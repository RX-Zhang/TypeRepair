int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    char startChar = seq.charAt(0);
    for (int offset = pos; offset < length; offset++) {
        // scan to first instance of startchar:
        if (startChar != input[offset])
            while (++offset < length && startChar != input[offset]);
        if (offset >= length)
            break;
        int i = offset + 1;
        int last = offset + seq.length();
        if (last > length)
            break;
        int j = 1;
        while (j < seq.length() && seq.charAt(j) == input[i]) {
            i++;
            j++;
        }
        if (j == seq.length()) // found full sequence
            return offset - pos;
    }
    return -1;
}
