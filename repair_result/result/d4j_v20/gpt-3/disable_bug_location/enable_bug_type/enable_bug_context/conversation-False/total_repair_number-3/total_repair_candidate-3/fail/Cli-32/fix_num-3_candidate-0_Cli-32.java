protected int findWrapPos(String text, int width, int startPos)
{
    int pos;
    
    // the line ends before the max wrap pos or a new line char found
    // note: pos must be compared with (startPos + width), not width alone
    pos = text.indexOf('\n', startPos);
    if (pos != -1 && pos <= startPos + width) {
        return pos + 1;
    }
    pos = text.indexOf('\t', startPos);
    if (pos != -1 && pos <= startPos + width) {
        return pos + 1;
    }
    
    if (startPos + width >= text.length()) {
        return -1;
    }
    
    // look for the last whitespace character before startPos+width
    pos = startPos + width;

    char c;

    while (pos > startPos && pos < text.length()
            && ((c = text.charAt(pos)) != ' ')
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

    // make sure pos is within bounds before accessing charAt
    while (pos < text.length()) {
        c = text.charAt(pos);
        if (c == ' ' || c == '\n' || c == '\r') {
            break;
        }
        ++pos;

        // Extra safety: break if pos reaches text.length()
        if (pos >= text.length()) {
            break;
        }
    }
    return pos == text.length() ? -1 : pos;
}
