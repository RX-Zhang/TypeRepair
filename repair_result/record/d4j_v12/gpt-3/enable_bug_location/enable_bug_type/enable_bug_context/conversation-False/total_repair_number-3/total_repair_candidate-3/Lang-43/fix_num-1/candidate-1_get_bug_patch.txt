private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        return appendTo == null ? null : appendTo.append(QUOTE);
    }
    int lastHold = start;
    for (; pos.getIndex() < pattern.length();) {
        int index = pos.getIndex();
        if (escapingOn && pattern.startsWith(ESCAPED_QUOTE, index)) {
            if (appendTo != null) {
                appendTo.append(c, lastHold, index - lastHold).append(QUOTE);
            }
            pos.setIndex(index + ESCAPED_QUOTE.length());
            lastHold = pos.getIndex();
            continue;
        }
        switch (c[index]) {
        case QUOTE:
            next(pos);
            if (appendTo != null) {
                appendTo.append(c, lastHold, pos.getIndex() - lastHold);
            }
            return appendTo;
        default:
            next(pos);
        }
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
