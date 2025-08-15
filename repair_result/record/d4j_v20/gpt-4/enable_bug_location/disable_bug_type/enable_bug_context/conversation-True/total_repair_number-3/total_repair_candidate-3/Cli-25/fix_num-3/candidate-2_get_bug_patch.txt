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

    int start = 0;

    while (true)
    {
        // Add padding to the start of the remaining text after pos
        text = padding + rtrim(text.substring(pos));
        start = nextLineTabStop;
        pos = findWrapPos(text, width, start);

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
