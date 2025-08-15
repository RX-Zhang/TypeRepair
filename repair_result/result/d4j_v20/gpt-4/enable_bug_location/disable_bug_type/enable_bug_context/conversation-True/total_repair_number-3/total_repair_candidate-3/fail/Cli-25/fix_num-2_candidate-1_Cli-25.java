protected StringBuffer renderWrappedText(StringBuffer sb, int width, 
                                         int nextLineTabStop, String text)
{
    int pos = 0;

    // first line does not have padding so find wrap position normally
    int wrapPos = findWrapPos(text, width, pos);

    if (wrapPos == -1)
    {
        sb.append(rtrim(text));

        return sb;
    }

    // first line
    sb.append(rtrim(text.substring(pos, wrapPos))).append(defaultNewLine);

    if (nextLineTabStop >= width)
    {
        // stops infinite loop happening
        nextLineTabStop = width - 1;
    }

    // create padding to be used on each subsequent line
    final String padding = createPadding(nextLineTabStop);

    pos = wrapPos;

    // while more text to render
    while (true)
    {
        text = padding + text.substring(pos).trim();

        wrapPos = findWrapPos(text, width, 0);

        if (wrapPos == -1)
        {
            sb.append(text);

            return sb;
        }

        if ( (text.length() > width) && (wrapPos == nextLineTabStop - 1) ) 
        {
            wrapPos = width;
        }

        sb.append(rtrim(text.substring(0, wrapPos))).append(defaultNewLine);

        pos = wrapPos;
    }
}
