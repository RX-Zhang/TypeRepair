private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        return appendTo == null ? null : appendTo.append(QUOTE);
    }
    int lastHold = start;
    while (pos.getIndex() < pattern.length()) {
        int i = pos.getIndex();
        if (escapingOn && pattern.startsWith(ESCAPED_QUOTE, i)) {
            if (appendTo != null) {
                appendTo.append(c, lastHold, i - lastHold).append(QUOTE);
            }
            pos.setIndex(i + ESCAPED_QUOTE.length());
            lastHold = pos.getIndex();
            continue;
        }
        char ch = c[pos.getIndex()];
        if (ch == QUOTE) {
            next(pos);
            if (appendTo != null) {
                appendTo.append(c, lastHold, pos.getIndex() - lastHold);
            }
            return appendTo;
        }
        next(pos);
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
