protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // Check for new line or tab character within the wrap width
    int newLinePos = text.indexOf('\n', startPos);
    if (newLinePos != -1 && newLinePos - startPos <= width) {
        return newLinePos + 1;
    }
    int tabPos = text.indexOf('\t', startPos);
    if (tabPos != -1 && tabPos - startPos <= width) {
        return tabPos + 1;
    }

    // If the remaining text length is less than or equal to width, no wrap position
    if (startPos + width >= text.length()) {
        return -1;
    }

    // Look backwards from startPos+width for whitespace or line break
    pos = startPos + width;
    while (pos > startPos) {
        char c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            return pos;
        }
        pos--;
    }

    // If no whitespace found backwards, look forwards for whitespace or line break
    pos = startPos + width;
    while (pos < text.length()) {
        char c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            return pos;
        }
        pos++;
    }

    // No wrap position found
    return -1;
}
