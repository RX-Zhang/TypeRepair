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

    int startPos = pos;
    while (true)
    {
        String remaining = text.substring(startPos).trim();
        String line = padding + remaining;
        pos = findWrapPos(remaining, width - nextLineTabStop, 0);

        if (pos == -1)
        {
            sb.append(line);

            return sb;
        }

        if (pos == 0)
        {
            throw new RuntimeException("Text too long for line - throwing exception to avoid infinite loop [CLI-162]: " + line);
        }

        sb.append(rtrim(line.substring(0, pos + nextLineTabStop))).append(defaultNewLine);
        startPos += pos;
    }
}
