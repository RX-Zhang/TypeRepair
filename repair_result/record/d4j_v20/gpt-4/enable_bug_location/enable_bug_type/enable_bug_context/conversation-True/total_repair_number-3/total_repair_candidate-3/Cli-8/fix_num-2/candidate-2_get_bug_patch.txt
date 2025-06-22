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
    int len = text.length();
    while (offset < len)
    {
        // Trim leading spaces in the substring to wrap
        String subText = text.substring(offset).trim();
        String line = padding + subText;

        pos = findWrapPos(line, width, nextLineTabStop);

        if (pos == -1)
        {
            sb.append(line);
            break;
        }
        else
        {
            sb.append(rtrim(line.substring(0, pos))).append(defaultNewLine);
        }

        offset += (line.substring(nextLineTabStop).substring(0, pos - nextLineTabStop).length());
    }

    return sb;
}
