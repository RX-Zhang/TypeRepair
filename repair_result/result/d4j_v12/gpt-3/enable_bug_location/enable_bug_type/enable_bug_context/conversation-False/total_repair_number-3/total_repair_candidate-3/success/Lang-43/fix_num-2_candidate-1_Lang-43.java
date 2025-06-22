private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        // advance past the initial quote before returning
        pos.setIndex(start + 1);
        return appendTo == null ? null : appendTo.append(QUOTE);
    }
    int lastHold = pos.getIndex();
    while (pos.getIndex() < pattern.length()) {
        if (escapingOn && pattern.startsWith(ESCAPED_QUOTE, pos.getIndex())) {
            if (appendTo != null) {
                appendTo.append(c, lastHold, pos.getIndex() - lastHold).append(QUOTE);
            }
            pos.setIndex(pos.getIndex() + ESCAPED_QUOTE.length());
            lastHold = pos.getIndex();
            continue;
        }
        char ch = c[pos.getIndex()];
        if (ch == QUOTE) {
            pos.setIndex(pos.getIndex() + 1);
            if (appendTo != null) {
                appendTo.append(c, lastHold, pos.getIndex() - lastHold - 1);
            }
            return appendTo;
        }
        pos.setIndex(pos.getIndex() + 1);
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
