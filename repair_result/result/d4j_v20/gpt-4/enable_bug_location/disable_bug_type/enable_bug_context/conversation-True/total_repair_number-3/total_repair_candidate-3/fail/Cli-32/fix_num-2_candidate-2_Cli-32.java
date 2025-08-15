protected int findWrapPos(String text, int width, int startPos)
{
    int pos;
    
    // the line ends before the max wrap pos or a new line char found
    if (((pos = text.indexOf('\n', startPos)) != -1 && pos <= startPos + width)
            || ((pos = text.indexOf('\t', startPos)) != -1 && pos <= startPos + width))
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
    = startPos + width;
    // ensure pos is less than text.length() before charAt call to avoid IndexOutOfBoundsException
    while ((pos < text.length()) && ((c = text.charAt(pos)) != ' ')
           && (c != '\n') && (c != '\r'))
    {
        ++pos;
    }
    
    // trim trailing whitespace if any
    String trimmedText = rtrim(text.substring(startPos, pos));
    int trimmedLength = trimmedText.length();

    if (trimmedLength == 0)
    {
        return -1;
    }

    if (pos == text.length())
    {
        return -1;
    }
    
    return startPos + trimmedLength;
}
