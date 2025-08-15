private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        return appendTo == null ? null : appendTo.append(QUOTE);
    }
    int lastHold = start;
    int index = pos.getIndex();
    while (index < pattern.length()) {
        if (escapingOn && pattern.startsWith(ESCAPED_QUOTE, index)) {
            appendTo.append(c, lastHold, index - lastHold).append(QUOTE);
            index += ESCAPED_QUOTE.length();
            lastHold = index;
            pos.setIndex(index);
            continue;
        }
        if (c[index] == QUOTE) {
            index++;
            pos.setIndex(index);
            return appendTo == null ? null : appendTo.append(c, lastHold, index - lastHold);
        }
        index++;
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
