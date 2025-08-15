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
        text = padding + text.substring(startPos).trim();
        pos = findWrapPos(text, width, 0);

        if (pos == -1)
        {
            sb.append(text);

            return sb;
        }

        // To prevent infinite loop, check if pos == 0 (no wrap pos found) or
        // pos did not advance past the initial padding length.
        if (pos == 0 || pos <= nextLineTabStop)
        {
            throw new RuntimeException("Text too long for line - throwing exception to avoid infinite loop [CLI-162]: " + text);
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);

        // Prepare for next iteration
        startPos = pos;
    }
}
