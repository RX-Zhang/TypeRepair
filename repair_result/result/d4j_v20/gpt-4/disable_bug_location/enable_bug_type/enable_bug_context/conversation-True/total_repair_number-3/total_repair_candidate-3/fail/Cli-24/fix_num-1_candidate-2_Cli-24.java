protected StringBuffer renderWrappedText(StringBuffer sb, int width, 
                                         int nextLineTabStop, String text)
{
    // Prevent widths smaller than nextLineTabStop to avoid IllegalStateException
    if (width <= nextLineTabStop) {
        // Adjust width to be at least one greater than nextLineTabStop
        width = nextLineTabStop + 1;
    }

    int pos = findWrapPos(text, width, 0);

    if (pos == -1)
    {
        sb.append(rtrim(text));

        return sb;
    }
    sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);

    // No longer throwing exception, since width adjusted above
    // all following lines must be padded with nextLineTabStop space 
    // characters
    final String padding = createPadding(nextLineTabStop);

    while (true)
    {
        text = padding + text.substring(pos).trim();
        pos = findWrapPos(text, width, 0);

        if (pos == -1)
        {
            sb.append(text);

            return sb;
        }
        
        if ( (text.length() > width) && (pos == nextLineTabStop - 1) ) 
        {
            pos = width;
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    }
}
