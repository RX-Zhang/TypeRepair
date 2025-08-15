protected int findWrapPos(String text, int width, int startPos)
{
    int pos;

    // the line ends before the max wrap pos or a new line char found
    int nl = text.indexOf('\n', startPos);
    int tab = text.indexOf('\t', startPos);
    int maxPos = startPos + width;

 if ((nl != -1 && nl <= maxPos) || (tab != -1 && tab <= maxPos))
    {
        pos = (nl != -1 && nl <= maxPos) ? nl : tab;
        return pos + 1;
    }
    else if (maxPos >= text.length())
    {
        return -1;
    }


    // look for the last whitespace character before startPos+width
    pos = maxPos;

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

    // if no whitespace found going backward, cut at width if possible,
    // or advance forward to next whitespace or end of string
    pos = maxPos;

    int length = text.length();

    while ((pos < length) && ((c = text.charAt(pos)) != ' ')
           && (c != '\n') && (c != '\r'))
    {
        ++pos;
    }

    return pos == length ? -1 : pos;
}
