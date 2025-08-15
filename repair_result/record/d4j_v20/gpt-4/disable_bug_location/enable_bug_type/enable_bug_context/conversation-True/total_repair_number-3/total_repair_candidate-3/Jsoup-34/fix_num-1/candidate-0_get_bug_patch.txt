int nextIndexOf(CharSequence seq) {
    if (seq.length() == 0) {
        return 0;
    }
    char startChar = seq.charAt(0);
    for (int offset = pos; offset < length; offset++) {
        // scan to first instance of startChar:
        if (startChar != input[offset]) {
            while (++offset < length && startChar != input[offset]);
        }
        if (offset >= length) {
            return -1;
        }
        int i = offset + 1;
        int last = offset + seq.length();
        if (last > length) {
            return -1;
        }
        int j = 1;
        while (i < last && seq.charAt(j) == input[i]) {
            i++;
            j++;
        }
        if (i == last) { // found full sequence
            return offset - pos;
        }
    }
    return -1;
}
