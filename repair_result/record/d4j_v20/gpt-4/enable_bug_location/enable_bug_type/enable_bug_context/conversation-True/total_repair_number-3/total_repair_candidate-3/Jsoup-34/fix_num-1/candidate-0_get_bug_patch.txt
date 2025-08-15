int nextIndexOf(CharSequence seq) {
    if (seq.length() == 0)
        return 0;
    char startChar = seq.charAt(0);
    for (int offset = pos; offset <= length - seq.length(); offset++) {
        if (input[offset] == startChar) {
            int i = offset + 1;
            int last = offset + seq.length();
            int j = 1;
            while (i < last && input[i] == seq.charAt(j)) {
                i++;
                j++;
            }
            if (i == last) {
                return offset - pos;
            }
        }
    }
    return -1;
}
