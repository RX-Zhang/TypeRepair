protected int findWrapPos(String text, int width, int startPos)
{
    int pos;
    
    // the line ends before the max wrap pos or a new line char found
    int newLinePos = text.indexOf('\n', startPos);
    int tabPos = text.indexOf('\t', startPos);
    if ((newLinePos != -1 && newLinePos <= startPos + width) ||
        (tabPos != -1 && tabPos <= startPos + width))
    {
        return Math.min(
            newLinePos != -1 ? newLinePos : Integer.MAX_VALUE,
            tabPos != -1 ? tabPos : Integer.MAX_VALUE) + 1;
    }
    else if (startPos + width >= text.length())
    {
        return -1;
    }

    // look for the last whitespace character before startPos+width
    pos = startPos + width;
    char c;

    while (pos > startPos && pos < text.length() &&
           ((c = text.charAt(pos)) != ' ') &&
           (c != '\n') && (c != '\r'))
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
    while (pos < text.length() &&
           ((c = text.charAt(pos)) != ' ') &&
           (c != '\n') && (c != '\r'))
    {
        ++pos;
    }
    return pos == text.length() ? -1 : pos;
}
