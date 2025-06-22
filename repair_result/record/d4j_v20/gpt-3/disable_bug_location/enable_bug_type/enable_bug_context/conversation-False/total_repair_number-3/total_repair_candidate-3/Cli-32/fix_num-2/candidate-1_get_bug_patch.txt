protected int findWrapPos(String text, int width, int startPos)
{
    int pos;
    
    // the line ends before the max wrap pos or a new line char found
    // Adjusted to check position relative to startPos rather than absolute
    int nlPos = text.indexOf('\n', startPos);
    if (nlPos != -1 && nlPos - startPos <= width) {
        return nlPos + 1;
    }
    int tabPos = text.indexOf('\t', startPos);
    if (tabPos != -1 && tabPos - startPos <= width) {
        return tabPos + 1;
    }

    if (startPos + width >= text.length()) {
        return -1;
    }

    // look for the last whitespace character before startPos+width
    pos = startPos + width;

    char c;

    // Adjusted loop condition to avoid StringIndexOutOfBounds and handle pos >= startPos correctly
    while (pos > startPos) {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            break;
        }
        --pos;
    }

    // if we found a whitespace or newline before the width limit, return it
    if (pos > startPos) {
        return pos;
    }

    // if we didn't find one, chop at startPos+width
    pos = startPos + width;

    // change the condition to pos < text.length() to avoid IndexOutOfBounds
    while (pos < text.length()) {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            break;
        }
        ++pos;
    }

    return pos == text.length() ? -1 : pos;
}
