int nextIndexOf(CharSequence seq) {
    if (seq.length() == 0)
        return 0;
    char startChar = seq.charAt(0);
    for (int offset = pos; offset <= length - seq.length(); offset++) {
        if (input[offset] == startChar) {
            int i = offset + 1;
            int j = 1;
            while (j < seq.length() && i < length && input[i] == seq.charAt(j)) {
                i++;
                j++;
            }
            if (j == seq.length())
                return offset - pos;
        }
    }
    return -1;
}
