private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        return appendTo == null ? null : appendTo.append(QUOTE);
    }
    int lastHold = start;
    int i = pos.getIndex();
    while (i < pattern.length()) {
        if (escapingOn && pattern.startsWith(ESCAPED_QUOTE, i)) {
            if (appendTo != null) {
                appendTo.append(c, lastHold, i - lastHold).append(QUOTE);
            }
            i += ESCAPED_QUOTE.length();
            pos.setIndex(i);
            lastHold = i;
            continue;
        }
        char ch = c[i];
        if (ch == QUOTE) {
            i++;
            pos.setIndex(i);
            if (appendTo != null) {
                appendTo.append(c, lastHold, i - lastHold);
            }
            return appendTo;
        }
        i++;
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
