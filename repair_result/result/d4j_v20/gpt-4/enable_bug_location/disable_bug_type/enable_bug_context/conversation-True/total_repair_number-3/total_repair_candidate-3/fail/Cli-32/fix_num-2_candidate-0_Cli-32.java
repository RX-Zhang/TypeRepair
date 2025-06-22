protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // the line ends before the max wrap pos or a new line char found
    int nextLine = text.indexOf('\n', startPos);
    int nextTab = text.indexOf('\t', startPos);

    if (nextLine != -1 && nextLine <= startPos + width) {
        return nextLine + 1;
    }
    if (nextTab != -1 && nextTab <= startPos + width) {
        return nextTab + 1;
    }

    if (startPos + width >= text.length())
    {
        return -1;
    }

    // look for the last whitespace character before startPos+width
    pos = startPos + width;

    char c;

    while (pos > startPos && pos < text.length() && (c = text.charAt(pos)) != ' ' && c != '\n' && c != '\r')
    {
        --pos;
    }

    if (pos > startPos)
    {
        return pos;
    }

    // no whitespace found, so force a break at startPos + width
    pos = startPos + width;
    while (pos < text.length() && (c = text.charAt(pos)) != ' ' && c != '\n' && c != '\r')
    {
        ++pos;
    }

    return pos == text.length() ? -1 : pos;
}
