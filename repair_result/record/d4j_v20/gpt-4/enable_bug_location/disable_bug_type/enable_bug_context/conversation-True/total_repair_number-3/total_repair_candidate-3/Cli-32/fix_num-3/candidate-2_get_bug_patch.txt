protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // the line ends before the max wrap pos or a new line char found
    pos = text.indexOf('\n', startPos);
    if (pos != -1 && pos <= startPos + width)
    {
        return pos + 1;
    }
    pos = text.indexOf('\t', startPos);
    if (pos != -1 && pos <= startPos + width)
    {
        return pos + 1;
    }

    if (startPos + width >= text.length())
    {
        return -1;
    }

    // look for the last whitespace character before startPos+width
    pos = startPos + width;

    char c;

    while ((pos > startPos) && ((c = text.charAt(pos)) != ' ')
            && (c != '\n') && (c != '\r'))
    {
        --pos;
    }

    // if we found it - just return
    if (pos > startPos)
    {
        return pos;
    }

    // if we didn't find one, simply chop at startPos+width
    pos = startPos + width;

    int len = text.length();
    while ((pos < len) && ((c = text.charAt(pos)) != ' ')
            && (c != '\n') && (c != '\r'))
    {
        ++pos;
    }

    return pos == len ? -1 : pos;
}
