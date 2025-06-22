private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        if (appendTo != null) {
            appendTo.append(QUOTE);
        }
        pos.setIndex(start + 1);
        return appendTo;
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
        char current = c[pos.getIndex()];
        switch (current) {
            case QUOTE:
                if (appendTo != null) {
                    appendTo.append(c, lastHold, pos.getIndex() - lastHold);
                }
                next(pos);
                return appendTo;
            default:
                next(pos);
        }
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
