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
        int lastPos = startPos;
        // Calculate next wrap position correctly with updated start position
        // Trim leading spaces in the remaining text
        String remainingText = text.substring(startPos).trim();
        String paddedText = padding + remainingText;
        pos = findWrapPos(paddedText, width, 0);

        if (pos == -1)
        {
            sb.append(paddedText);

            return sb;
        }
        else if (pos == lastPos)
        {
            throw new RuntimeException("Text too long for line - throwing exception to avoid infinite loop [CLI-162]: " + paddedText);
        }

        sb.append(rtrim(paddedText.substring(0, pos))).append(defaultNewLine);
        startPos += (pos - nextLineTabStop);
    }
}
