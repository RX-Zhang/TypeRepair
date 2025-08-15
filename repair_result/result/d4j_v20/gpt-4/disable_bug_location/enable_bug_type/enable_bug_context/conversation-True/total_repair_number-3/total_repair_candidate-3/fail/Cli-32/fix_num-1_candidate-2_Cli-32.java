protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    if (text == null || text.length() == 0) {
        return -1;
    }

    // the line ends before the max wrap pos or a new line char found
    int nlPos = text.indexOf('\n', startPos);
    int tabPos = text.indexOf('\t', startPos);

    // Check if newline or tab is found before startPos + width
    if (nlPos != -1 && nlPos <= startPos + width) {
        return nlPos + 1;
    }
    if (tabPos != -1 && tabPos <= startPos + width) {
        return tabPos + 1;
    }

    if (startPos + width >= text.length()) {
        return -1;
    }

    // look for the last whitespace character before startPos + width
    pos = startPos + width;

    while (pos > startPos) {
        char c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            return pos;
        }
        pos--;
    }

    // if we didn't find one, simply chop at startPos + width
    pos = startPos + width;

    while (pos < text.length()) {
        char c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            break;
        }
        pos++;
    }

    return pos == text.length() ? -1 : pos;
}
