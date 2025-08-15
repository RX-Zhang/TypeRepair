protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // Check for newline or tab characters within the wrap width
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

    // If the remaining text is shorter than the width, no wrapping needed
    if (startPos + width >= text.length())
    {
        return -1;
    }

    // look for the last whitespace character before startPos+width
    pos = startPos + width;
    char c;

    // Move backwards until finding a whitespace/newline or startPos
    while (pos > startPos)
    {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            return pos;
        }
        pos--;
    }

    // No whitespace found before startPos + width, so look forward
    pos = startPos + width + 1; // start searching from next char after width position

    while (pos < text.length())
    {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r')
        {
            return pos;
        }
        pos++;
    }

    // If no whitespace found forward, return -1 indicating no wrap position
    return -1;
}
