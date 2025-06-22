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

    if (nextLineTabStop >= width)
    {
        // stops infinite loop happening
        nextLineTabStop = width - 1;
    }

    // all following lines must be padded with nextLineTabStop space 
    // characters
    final String padding = createPadding(nextLineTabStop);
    int offset = 0;

    while (true)
    {
        // Adjust offset to avoid infinite loop
        text = padding + text.substring(pos).trim();
        offset = padding.length();
        pos = findWrapPos(text, width, offset);

        if (pos == -1)
        {
            sb.append(text);

            return sb;
        }

        if ((text.length() > width) && (pos == nextLineTabStop))
        {
            pos = width;
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    }
}
