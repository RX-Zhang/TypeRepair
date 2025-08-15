protected int findWrapPos(String text, int width, int startPos)
{
    int pos;
    
    // the line ends before the max wrap pos or a new line char or tab found
    if ((pos = text.indexOf('\n', startPos)) != -1 && pos - startPos <= width)
    {
        return pos + 1;
    }
    if ((pos = text.indexOf('\t', startPos)) != -1 && pos - startPos <= width)
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
            break;
        }
        pos--;
    }

    // if we found it - just return
    if (pos > startPos)
    {
        return pos;
    }
    
    // if we didn't find one, simply chop at startPos+width
    pos = startPos + width;
    int textLength = text.length();
    while (pos < textLength)
    {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            break;
        }
        pos++;
    }        
    return pos == textLength ? -1 : pos;
}
