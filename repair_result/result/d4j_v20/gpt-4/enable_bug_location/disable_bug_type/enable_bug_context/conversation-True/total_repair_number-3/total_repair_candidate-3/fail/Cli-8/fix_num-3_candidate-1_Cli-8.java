protected StringBuffer renderWrappedText(StringBuffer sb, int width, 
                                         int nextLineTabStop, String text)
{
    int pos = findWrapPos(text, width, 0);

    if (pos == -1)
    {
        sb.append(rtrim(text));

        return sb;
    }
    sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);

    // all following lines must be padded with nextLineTabStop space 
    // characters
    final String padding = createPadding(nextLineTabStop);

    int textPos = pos;
    int textLength = text.length();

    while (textPos < textLength)
    {
        // Remove leading spaces and new lines before padding
        while (textPos < textLength && 
               (text.charAt(textPos) == ' ' || text.charAt(textPos) == '\n' || text.charAt(textPos) == '\r'))
        {
            textPos++;
        }
        
        int nextPos = findWrapPos(text, width, textPos);
        String line;
        if (nextPos == -1)
        {
            line = text.substring(textPos);
        }
        else
        {
            line = text.substring(textPos, nextPos);
        }
        
        sb.append(padding).append(rtrim(line)).append(defaultNewLine);

        if (nextPos == -1)
        {
            break;
        }

        textPos = nextPos;
    }

    return sb;
}
