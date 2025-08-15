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

    int startPos = 0;
    int textLen = text.length();

    while (pos < textLen)
    {
        startPos = pos;

        // trim the substring from startPos for next iteration
        // but do not trim padding spaces that were added previously
        text = padding + rtrim(text.substring(startPos)).trim();

        pos = findWrapPos(text, width, padding.length());

        if (pos == -1)
        {
            sb.append(text);

            return sb;
        }
        else if (pos == startPos)
        {
            throw new RuntimeException("Text too long for line - throwing exception to avoid infinite loop [CLI-162]: " + text);
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    }

    return sb;
}
