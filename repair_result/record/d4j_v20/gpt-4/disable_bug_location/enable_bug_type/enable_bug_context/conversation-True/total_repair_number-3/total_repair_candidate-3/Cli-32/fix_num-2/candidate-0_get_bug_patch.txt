protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // Defensive checks for invalid inputs
    if (text == null)
    {
        return -1;
    }

    int maxPos = text.length() - 1;

    // the line ends before the max wrap pos or a new line/tab char found
    int newLinePos = text.indexOf('\n', startPos);
    if (newLinePos != -1 && newLinePos <= startPos + width)
    {
        return newLinePos + 1;
    }
    int tabPos = text.indexOf('\t', startPos);
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

    while (pos >= startPos)
    {
        char c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            return pos;
        }
        pos--;
    }

    // if no whitespace found before width, look forward for whitespace or linebreak
    pos = startPos + width + 1;

    while (pos <= maxPos)
    {
        char c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            return pos;
        }
        pos++;
    }

    // no wrap pos found; return -1
    return -1;
}
