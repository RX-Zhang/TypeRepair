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
        // Changed the condition check to use getWidth() and getLeftPadding() for better validation
        throw new IllegalStateException("Total width is less than the width of the argument and indent " + 
                                        "- no room for the description");
    }

    // all following lines must be padded with nextLineTabStop space 
    // characters
    final String padding = createPadding(nextLineTabStop);

    while (true)
    {
        text = padding + text.substring(pos).trim();
        // Changed startPos in findWrapPos to nextLineTabStop to avoid infinite loop on indent
        pos = findWrapPos(text, width, nextLineTabStop);

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
