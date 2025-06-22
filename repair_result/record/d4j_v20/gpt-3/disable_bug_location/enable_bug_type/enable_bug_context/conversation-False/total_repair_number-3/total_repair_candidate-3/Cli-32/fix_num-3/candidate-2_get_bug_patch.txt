protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // Check for newline or tab within the wrap width range
    int nlPos = text.indexOf('\n', startPos);
    int tabPos = text.indexOf('\t', startPos);

    if ((nlPos != -1 && nlPos <= startPos + width) || (tabPos != -1 && tabPos <= startPos + width)) {
        pos = (nlPos != -1 && nlPos <= startPos + width) ? nlPos : tabPos;
        return pos + 1;
    }

    if (startPos + width >= text.length()) {
        return -1;
    }

    // Look for the last whitespace character (space, \n, \r) before startPos+width
    pos = startPos + width;
    char c;

    while (pos > startPos) {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            return pos;
        }
        pos--;
    }

    // No whitespace found before width, now look forward to find a whitespace or end of string 
    pos = startPos + width;

    while (pos < text.length()) {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            return pos;
        }
        pos++;
    }

    // No whitespace found at all
    return -1;
}
