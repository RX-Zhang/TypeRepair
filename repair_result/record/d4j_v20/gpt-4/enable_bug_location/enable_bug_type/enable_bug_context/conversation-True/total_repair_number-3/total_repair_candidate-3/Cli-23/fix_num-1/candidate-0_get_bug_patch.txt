protected StringBuffer renderWrappedText(StringBuffer sb, int width, 
                                         int nextLineTabStop, String text)
{
    if (width < 1) {
        throw new IllegalArgumentException("Width must be positive");
    }

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
        int lastPos = pos;
        // Trim text from startPos, add padding at the beginning
        String trimmedText = text.substring(startPos).trim();
        text = padding + trimmedText;
        pos = findWrapPos(text, width, 0);

        if (pos == -1)
        {
            sb.append(text);

            return sb;
        }
        else if (pos == lastPos)
        {
            // This indicates no progress in wrapping; to avoid infinite loop, 
            // forcibly break the line at width or at the length of the text
            if (width < text.length()) {
                sb.append(rtrim(text.substring(0, width))).append(defaultNewLine);
                text = padding + text.substring(width).trim();
                pos = findWrapPos(text, width, 0);
                startPos = 0;
                continue;
            } else {
                sb.append(text);
                return sb;
            }
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
        startPos = pos;
    }
}
