private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        if (appendTo != null) {
            appendTo.append(QUOTE);
        }
        pos.setIndex(start + 2); // Move past the escaped quote ''
        return appendTo;
    }
    int lastHold = start;
    while (pos.getIndex() < pattern.length()) {
        int currentIndex = pos.getIndex();
        if (escapingOn && pattern.startsWith(ESCAPED_QUOTE, currentIndex)) {
            if (appendTo != null) {
                appendTo.append(c, lastHold, currentIndex - lastHold).append(QUOTE);
            }
            pos.setIndex(currentIndex + ESCAPED_QUOTE.length());
            lastHold = pos.getIndex();
            continue;
        }
        char ch = c[currentIndex];
        if (ch == QUOTE) {
            pos.setIndex(currentIndex + 1);
            if (appendTo != null) {
                appendTo.append(c, lastHold, pos.getIndex() - lastHold - 1);
            }
            return appendTo;
        }
        pos.setIndex(currentIndex + 1);
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
