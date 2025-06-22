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

    int start = pos;

    while (true)
    {
        text = padding + text.substring(start).trim();
        pos = findWrapPos(text, width, 0);

        if (pos == -1)
        {
            sb.append(text);

            return sb;
        }
        else if (pos == 0)
        {
            // no wrap position found without infinite loop risk, append all and return
            sb.append(text);

            return sb;
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);

        start = pos;
    }
}
