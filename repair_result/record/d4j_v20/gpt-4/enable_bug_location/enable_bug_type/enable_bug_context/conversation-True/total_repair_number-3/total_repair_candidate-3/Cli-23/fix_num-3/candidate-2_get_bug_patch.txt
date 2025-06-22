protected StringBuffer renderWrappedText(StringBuffer sb, int width, 
                                         int nextLineTabStop, String text)
{
    int pos = findWrapPos(text, width, 0);
    int startPos = 0;

    if (pos == -1)
    {
        sb.append(rtrim(text));

        return sb;
    }
    sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    startPos = pos;

    // all following lines must be padded with nextLineTabStop space 
    // characters
    final String padding = createPadding(nextLineTabStop);

    while (true)
    {
        int lastPos = pos;
        // Adjust startPos for next search after the trimmed substring
        String trimmedSubstring = text.substring(startPos).trim();
        text = padding + trimmedSubstring;
        pos = findWrapPos(text, width, 0);
        if (pos == -1)
        {
            sb.append(text);

            return sb;
        }
        else if (pos == lastPos)
        {
            throw new RuntimeException("Text too long for line - throwing exception to avoid infinite loop [CLI-162]: " + text);
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
        startPos += lastPos;
    }
}
