protected StringBuffer renderWrappedText(StringBuffer sb, int width, 
                                         int nextLineTabStop, String text)
{
    int pos = findWrapPos(text, width, 0);

    if (pos == -1)
    {
        sb.append(rtrim(text));
        return sb;
    }
    
    sb.append(rtrim(text.substring(0, pos))).append(getNewLine());

    // all following lines must be padded with nextLineTabStop space 
    // characters
    final String padding = createPadding(nextLineTabStop);

    while (true)
    {
        text = text.substring(pos).trim(); // Update text to remove processed part
        pos = findWrapPos(text, width, nextLineTabStop);
        
        if (pos == -1)
        {
            sb.append(padding).append(rtrim(text)); // Add padding before the last segment
            return sb;
        }

        sb.append(padding).append(rtrim(text.substring(0, pos))).append(getNewLine());
    }
}
