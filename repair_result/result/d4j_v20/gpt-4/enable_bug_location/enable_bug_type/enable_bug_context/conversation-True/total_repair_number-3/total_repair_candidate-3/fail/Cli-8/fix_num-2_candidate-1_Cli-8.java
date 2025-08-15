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

    int offset = pos;
    int textLen = text.length();

    while (true)
    {
        // skip whitespace at the offset position
        while (offset < textLen && Character.isWhitespace(text.charAt(offset))) 
        {
            offset++;
        }

        if (offset >= textLen)
        {
            return sb;
        }

        String line = padding + text.substring(offset);
        pos = findWrapPos(line, width, nextLineTabStop);

        if (pos == -1)
        {
            sb.append(line);

            return sb;
        }

        sb.append(rtrim(line.substring(0, pos))).append(defaultNewLine);

        offset += pos - nextLineTabStop;
    }
}
