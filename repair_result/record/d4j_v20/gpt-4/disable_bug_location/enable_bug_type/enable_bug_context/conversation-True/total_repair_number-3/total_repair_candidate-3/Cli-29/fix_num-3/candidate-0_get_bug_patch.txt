static String stripLeadingAndTrailingQuotes(String str)
{
    if (str == null || str.isEmpty()) {
        return str;
    }

    int start = 0;
    int end = str.length();

    if (str.startsWith("\""))
    {
        start = 1;
    }
    if (str.endsWith("\"") && end > start)
    {
        end = end - 1;
    }

    return str.substring(start, end);
}
