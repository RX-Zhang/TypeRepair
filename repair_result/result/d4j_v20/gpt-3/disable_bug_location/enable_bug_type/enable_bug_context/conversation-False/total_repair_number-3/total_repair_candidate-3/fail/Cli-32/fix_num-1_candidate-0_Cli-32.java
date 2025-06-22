protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // the line ends before the max wrap pos or a new line char found
    int newLinePos = text.indexOf('\n', startPos);
    if (newLinePos != -1 && newLinePos <= startPos + width) {
        return newLinePos + 1;
    }
    int tabPos = text.indexOf('\t', startPos);
    if (tabPos != -1 && tabPos <= startPos + width) {
        return tabPos + 1;
    }

    if (startPos + width >= text.length()) {
        return -1;
    }

    // look for the last whitespace character before startPos+width
    pos = startPos + width;
    char c;

    while (pos > startPos && ((c = text.charAt(pos)) != ' ') 
           && (c != '\n') && (c != '\r')) 
    {
        pos--;
    }

    // if we found it - just return
    if (pos > startPos) {
        return pos;
    }

    // if we didn't find one, simply chop at startPos+width and expand forward to find a whitespace or line break
    pos = startPos + width;
    while (pos < text.length() && ((c = text.charAt(pos)) != ' ') && (c != '\n') && (c != '\r')) {
        pos++;
    }

    return (pos == text.length()) ? -1 : pos;
}
