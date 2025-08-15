int nextIndexOf(CharSequence seq) {
    char startChar = seq.charAt(0);
    for (int offset = pos; offset <= length - seq.length(); offset++) {
        if (input[offset] == startChar) {
            boolean found = true;
            for (int j = 1; j < seq.length(); j++) {
                if (input[offset + j] != seq.charAt(j)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return offset - pos;
            }
        }
    }
    return -1;
}
