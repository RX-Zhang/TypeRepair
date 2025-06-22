protected int findWrapPos(String text, int width, int startPos)
{
    int pos;
    
    // Check for newline or tab before startPos+width
    int newLinePos = text.indexOf('\n', startPos);
    int tabPos = text.indexOf('\t', startPos);

    if (newLinePos != -1 && newLinePos <= startPos + width)
    {
        return newLinePos + 1;
    }
    if (tabPos != -1 && tabPos <= startPos + width)
    {
        return tabPos + 1;
    }
    
    if (startPos + width >= text.length())
    {
        return -1;
    }


    // look for the last whitespace character before startPos+width
    pos = startPos + width;

    char c;

    while (pos > startPos) {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            return pos;
        }
        pos--;
    }

    // if no whitespace found before startPos+width, look forward from startPos+width
    pos = startPos + width;
    while (pos < text.length()) {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            return pos;
        }
        pos++;
    }

    return -1;
}
