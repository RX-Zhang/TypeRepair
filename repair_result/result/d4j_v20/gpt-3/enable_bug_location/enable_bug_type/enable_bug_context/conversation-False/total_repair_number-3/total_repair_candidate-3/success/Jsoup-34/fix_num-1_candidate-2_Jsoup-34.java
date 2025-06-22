int nextIndexOf(CharSequence seq) {
    // doesn't handle scanning for surrogates
    if (seq == null || seq.length() == 0) return -1; // guard against empty seq
    char startChar = seq.charAt(0);
    for (int offset = pos; offset < length; offset++) {
        // scan to first instance of startchar:
        if (startChar != input[offset]) {
            while (++offset < length && startChar != input[offset]);
        }
        if (offset >= length) break;
        int i = offset + 1;
        int last = offset + seq.length();
        if (last > length) {
            // cannot match if sequence would run past end of input
            break;
        }
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
