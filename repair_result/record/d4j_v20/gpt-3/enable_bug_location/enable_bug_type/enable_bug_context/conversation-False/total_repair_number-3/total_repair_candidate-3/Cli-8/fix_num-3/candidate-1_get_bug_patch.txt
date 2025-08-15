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

    while (true)
    {
        text = text.substring(pos).trim(); // Trim the text before adding padding
        text = padding + text; // Add padding
        pos = findWrapPos(text, width, 0); // Reset nextLineTabStop to 0 for wrapping

        if (pos == -1)
        {
            sb.append(text);

            return sb;
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    }
}
