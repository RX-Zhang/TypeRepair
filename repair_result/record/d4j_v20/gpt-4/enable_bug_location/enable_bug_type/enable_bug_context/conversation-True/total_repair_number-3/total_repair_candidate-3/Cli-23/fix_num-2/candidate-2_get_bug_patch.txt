protected StringBuffer renderWrappedText(StringBuffer sb, int width, 
                                         int nextLineTabStop, String text)
{
    int pos = findWrapPos(text, width, 0);
    int start = 0;

    if (pos == -1)
    {
        sb.append(rtrim(text));

        return sb;
    }
    sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);

    // all following lines must be padded with nextLineTabStop space 
    // characters
    final String padding = createPadding(nextLineTabStop);

    start = pos;

    while (true)
    {
        int lastPos = pos;
        // trim leading spaces from the remaining text to wrap
        while (start < text.length() && Character.isWhitespace(text.charAt(start))) {
            start++;
        }

        // if no more text, break
        if (start >= text.length()) {
            break;
        }

        String line = padding + text.substring(start);
        pos = findWrapPos(line, width, 0);

        if (pos == -1)
        {
            sb.append(line);

            return sb;
        } 
        // Prevent infinite loop
        if (pos == 0)
        {
            pos = width;
        }

        sb.append(rtrim(line.substring(0, pos))).append(defaultNewLine);
        start += pos - padding.length();
    }
}
