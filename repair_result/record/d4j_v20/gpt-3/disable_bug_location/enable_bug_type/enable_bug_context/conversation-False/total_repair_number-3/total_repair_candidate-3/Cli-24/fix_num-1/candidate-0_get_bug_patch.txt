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
        throw new IllegalStateException("Total width is less than the width of the argument and indent " + 
                                        "- no room for the description");
    }

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
        
        // fix: correct the condition to check if pos == nextLineTabStop
        // instead of nextLineTabStop -1, because pos is index, and padding occupies from 0 to nextLineTabStop-1
        if ( (text.length() > width) && (pos == nextLineTabStop) ) 
        {
            pos = width;
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    }
}
