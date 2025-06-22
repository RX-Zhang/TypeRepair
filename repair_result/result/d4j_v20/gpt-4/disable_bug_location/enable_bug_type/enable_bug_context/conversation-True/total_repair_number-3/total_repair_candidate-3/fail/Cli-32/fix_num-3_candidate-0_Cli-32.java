protected int findWrapPos(String text, int width, int startPos)
{
    int pos;
    
    // the line ends before the max wrap pos or a new line char found
    if (((pos = text.indexOf('\n', startPos)) != -1 && pos - startPos <= width)
            || ((pos = text.indexOf('\t', startPos)) != -1 && pos - startPos <= width))
    {
        return pos + 1;
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

    // if we didn't find one, simply chop at startPos+width and try to extend to next whitespace
    pos = startPos + width;

    while (pos < text.length())
    {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            break;
        }
        pos++;
    }

    return pos == text.length() ? -1 : pos;
}
