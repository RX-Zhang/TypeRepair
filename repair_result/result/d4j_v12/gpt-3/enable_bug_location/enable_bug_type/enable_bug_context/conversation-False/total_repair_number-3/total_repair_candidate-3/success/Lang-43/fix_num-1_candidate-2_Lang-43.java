private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        pos.setIndex(start + 1);
        return appendTo == null ? null : appendTo.append(QUOTE);
    }
    int lastHold = start;
    while (pos.getIndex() < pattern.length()) {
        int index = pos.getIndex();
        if (escapingOn && pattern.startsWith(ESCAPED_QUOTE, index)) {
            if (appendTo != null) {
                appendTo.append(c, lastHold, index - lastHold).append(QUOTE);
            }
            pos.setIndex(index + ESCAPED_QUOTE.length());
            lastHold = pos.getIndex();
            continue;
        }
        char ch = c[index];
        if (ch == QUOTE) {
            pos.setIndex(index + 1);
            if (appendTo != null) {
                appendTo.append(c, lastHold, pos.getIndex() - lastHold);
            }
            return appendTo;
        } else {
            pos.setIndex(index + 1);
        }
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
