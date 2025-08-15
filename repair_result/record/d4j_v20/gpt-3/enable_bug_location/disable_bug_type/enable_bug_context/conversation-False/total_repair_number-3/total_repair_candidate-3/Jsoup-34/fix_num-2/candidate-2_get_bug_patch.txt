int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    for (int offset = pos; offset < length; offset++) {
        // scan to first instance of startChar:
        if (startChar != input[offset]) {
            while (++offset < length && startChar != input[offset]);
        }
        if (offset >= length)
            break; // no more possible matches
        
        int i = offset + 1;
        int last = offset + seqLength;
        if (last > length)
            break; // remaining input too short to match sequence
        
        int j = 1;
        for (; i < last && seq.charAt(j) == input[i]; i++, j++);
        
        if (i == last) // found full sequence
            return offset - pos;
    }
    return -1;
}
