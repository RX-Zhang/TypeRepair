static String stripLeadingAndTrailingQuotes(String str)
{
    if (str.length() > 1 && str.startsWith("\"") && str.endsWith("\""))
    {
        str = str.substring(1, str.length() - 1);
    }
    return str;
}
