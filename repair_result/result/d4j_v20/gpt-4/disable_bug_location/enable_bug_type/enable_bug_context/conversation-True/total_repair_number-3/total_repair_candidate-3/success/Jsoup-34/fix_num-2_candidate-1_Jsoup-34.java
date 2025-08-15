int nextIndexOf(CharSequence seq) {
    if (seq == null || seq.length() == 0)
        return -1;

    char startChar = seq.charAt(0);
    for (int offset = pos; offset <= length - seq.length(); offset++) {
        if (input[offset] != startChar)
            continue;

        int i = offset + 1;
        int last = offset + seq.length();
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
