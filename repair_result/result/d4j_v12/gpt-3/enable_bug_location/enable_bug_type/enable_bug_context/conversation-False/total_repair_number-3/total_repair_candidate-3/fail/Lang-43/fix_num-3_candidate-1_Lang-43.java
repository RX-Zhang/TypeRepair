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
            if (appendTo != null) {
                appendTo.append(c, lastHold, index - lastHold).append(QUOTE);
            }
            index += ESCAPED_QUOTE.length();
            pos.setIndex(index);
            lastHold = index;
            continue;
        }
        char currentChar = c[index];
        if (currentChar == QUOTE) {
            index++;
            pos.setIndex(index);
            if (appendTo != null) {
                appendTo.append(c, lastHold, index - lastHold);
            }
            return appendTo;
        }
        index++;
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
