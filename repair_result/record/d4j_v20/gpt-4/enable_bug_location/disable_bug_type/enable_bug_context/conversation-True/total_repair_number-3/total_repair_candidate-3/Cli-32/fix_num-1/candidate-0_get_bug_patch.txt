protected int findWrapPos(String text, int width, int startPos)
{
    int pos;
    
    int max = startPos + width;
    if (max >= text.length())
    {
        return -1;
    }
    
    // the line ends before the max wrap pos or a new line char found
    int newLinePos = text.indexOf('\n', startPos);
    if (newLinePos != -1 && newLinePos <= max)
    {
        return newLinePos + 1;
    }
    int tabPos = text.indexOf('\t', startPos);
    if (tabPos != -1 && tabPos <= max)
    {
        return tabPos + 1;
    }

    // look for the last whitespace character before max
    pos = max;

    char c;

    while (pos >= startPos)
    {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            break;
        }
        pos--;
    }

    if (pos > startPos)
    {
        return pos;
    }

    // if no whitespace found before max, look for the next whitespace after max
    pos = max;
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
