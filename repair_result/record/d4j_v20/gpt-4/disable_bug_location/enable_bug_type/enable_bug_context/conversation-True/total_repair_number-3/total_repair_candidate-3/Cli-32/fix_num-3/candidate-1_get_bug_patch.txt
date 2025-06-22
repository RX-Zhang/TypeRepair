protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // the line ends before the max wrap pos or a new line char found
    // Check if newline or tab is within the allowed width after startPos
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

    while (pos > startPos) // use pos > startPos to avoid infinite loop at startPos==0 and pos=0
    {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            return pos;
        }
        pos--;
    }

    // if we didn't find a whitespace before startPos+width, chop at startPos+width (if possible)
    pos = startPos + width;

    while (pos < text.length())
    {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            return pos;
        }
        pos++;
    }

    return -1;
}
