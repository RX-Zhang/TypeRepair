private StringBuffer appendQuotedString(String pattern, ParsePosition pos,
        StringBuffer appendTo, boolean escapingOn) {
    int start = pos.getIndex();
    char[] c = pattern.toCharArray();
    if (escapingOn && c[start] == QUOTE) {
        pos.setIndex(start + 1);
        return appendTo == null ? null : appendTo.append(QUOTE);
    }
    int lastHold = start;
    for (int i = pos.getIndex(); i < pattern.length(); ) {
        if (escapingOn && pattern.substring(i).startsWith(ESCAPED_QUOTE)) {
            appendTo.append(c, lastHold, i - lastHold).append(QUOTE);
            i += ESCAPED_QUOTE.length();
            pos.setIndex(i);
            lastHold = pos.getIndex();
            continue;
        }
        switch (c[i]) {
        case QUOTE:
            appendTo.append(c, lastHold, i - lastHold);
            i++; 
            pos.setIndex(i);
            return appendTo;
        default:
            i++;
        }
    }
    throw new IllegalArgumentException(
            "Unterminated quoted string at position " + start);
}
