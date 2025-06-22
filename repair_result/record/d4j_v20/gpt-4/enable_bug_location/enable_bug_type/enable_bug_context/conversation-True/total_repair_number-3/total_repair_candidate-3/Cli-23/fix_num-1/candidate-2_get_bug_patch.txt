protected StringBuffer renderWrappedText(StringBuffer sb, int width, 
                                         int nextLineTabStop, String text)
{
    if (width <= 0)
    {
        throw new IllegalArgumentException("Width must be positive.");
    }
    if (text == null)
    {
        return sb;
    }

    int pos = -1;
    int startPos = 0;
    final String padding = createPadding(nextLineTabStop);

    while (startPos < text.length())
    {
        pos = findWrapPos(text, width, startPos);

        // no wrap position found or line fits in width
        if (pos == -1)
        {
            sb.append(rtrim(text.substring(startPos)));
            break;
        }

        // avoid infinite loop when findWrapPos returns startPos or less
        if (pos <= startPos)
        {
            // forcibly wrap at width (or end of line if shorter)
            pos = Math.min(startPos + width, text.length());
            sb.append(rtrim(text.substring(startPos, pos))).append(defaultNewLine);
            startPos = pos;
        }
        else
        {
            sb.append(rtrim(text.substring(startPos, pos))).append(defaultNewLine);
            startPos = pos;

            // skip whitespace characters at start of next line
            while (startPos < text.length() && Character.isWhitespace(text.charAt(startPos)))
            {
                startPos++;
            }

            // insert padding before next line text
            sb.append(padding);
        }
    }

    return sb;
}
