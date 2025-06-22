protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // the line ends before the max wrap pos or a new line char found
    int nextLinePos = text.indexOf('\n', startPos);
    if (nextLinePos != -1 && nextLinePos <= startPos + width)
    {
        return nextLinePos + 1;
    }
    int nextTabPos = text.indexOf('\t', startPos);
    if (nextTabPos != -1 && nextTabPos <= startPos + width)
    {
        return nextTabPos + 1;
    }
    else if (startPos + width >= text.length())
    {
        return -1;
    }

    // look for the last whitespace character before startPos+width
    pos = startPos + width;

    char c;

    while (pos > startPos)
    {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            return pos;
        }
        pos--;
    }

    // if we didn't find one, simply chop at startPos+width
    pos = startPos + width;
    int textLength = text.length();

    while (pos < textLength)
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
