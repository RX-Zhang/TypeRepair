private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        pos.setIndex(start + 1);
        return appendTo == null ? null : appendTo.append(QUOTE);
    }
    int lastHold = start;
    for (;;) {
        if (pos.getIndex() >= pattern.length()) {
            break;
        }
        int i = pos.getIndex();
        if (escapingOn && pattern.startsWith(ESCAPED_QUOTE, i)) {
            appendTo.append(c, lastHold, i - lastHold).append(QUOTE);
            pos.setIndex(i + ESCAPED_QUOTE.length());
            lastHold = pos.getIndex();
            continue;
        }
        char ch = c[i];
        if (ch == QUOTE) {
            next(pos);
            return appendTo == null ? null : appendTo.append(c, lastHold,
                    pos.getIndex() - lastHold);
        }
        next(pos);
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
