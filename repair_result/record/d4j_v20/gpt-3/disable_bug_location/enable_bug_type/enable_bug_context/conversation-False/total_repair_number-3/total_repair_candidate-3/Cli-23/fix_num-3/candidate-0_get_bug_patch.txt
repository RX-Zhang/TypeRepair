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

    // Track the last wrap position to detect no progress
    while (true)
    {
        int lastPos = pos;
        text = padding + text.substring(pos).trim();
        pos = findWrapPos(text, width, 0);

        // If no wrapping position found, append remainder and return
        if (pos == -1)
        {
            sb.append(text);

            return sb;
        }

        // Prevent infinite loop: if new pos is zero or does not move forward 
        // compared to lastPos, throw exception
        if (pos == lastPos || pos == 0)
        {
            throw new RuntimeException("Text too long for line - throwing exception to avoid infinite loop [CLI-162]: " + text);
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    }
}
