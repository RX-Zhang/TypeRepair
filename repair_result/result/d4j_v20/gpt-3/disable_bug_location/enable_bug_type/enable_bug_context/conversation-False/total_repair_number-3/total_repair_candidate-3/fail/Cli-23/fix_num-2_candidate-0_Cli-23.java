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
        // Save text before modification in order to detect infinite loop correctly
        String currentText = text;

        text = padding + text.substring(pos).trim();
        pos = findWrapPos(text, width, 0);

        if (pos == -1)
        {
            sb.append(text);

            return sb;
        } 
        if (pos == text.length()) // if wrap pos is at the very end, cannot break more
        {
            sb.append(text).append(defaultNewLine);
            return sb;
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    }
}
