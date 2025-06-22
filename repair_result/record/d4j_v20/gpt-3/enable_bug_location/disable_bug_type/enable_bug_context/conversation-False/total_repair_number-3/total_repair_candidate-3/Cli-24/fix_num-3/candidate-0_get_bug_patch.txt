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
        text = text.substring(pos).trim();

        // If the remaining text length plus padding is less than or equal to width,
        // then append padding + text and return.
        if (text.length() + nextLineTabStop <= width) {
            sb.append(padding).append(text);
            return sb;
        }

        pos = findWrapPos(text, width - nextLineTabStop, 0);

        if (pos == -1)
        {
            sb.append(padding).append(text);

            return sb;
        }
        
        sb.append(padding).append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    }
}
