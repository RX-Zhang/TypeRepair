protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // the line ends before the max wrap pos or a new line char found
    int wrapPos = text.indexOf('\n', startPos);
    if (wrapPos != -1 && wrapPos <= startPos + width)
    {
        return wrapPos + 1;
    }
    wrapPos = text.indexOf('\t', startPos);
    if (wrapPos != -1 && wrapPos <= startPos + width)
    {
        return wrapPos + 1;
    }

    if (startPos + width >= text.length())
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
