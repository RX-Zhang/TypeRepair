static String stripLeadingAndTrailingQuotes(String str)
{
    if (str.length() == 1 && str.equals("\"")) {
        return str;
    }
    if (str.startsWith("\""))
    {
        str = str.substring(1);
    }
    int length = str.length();
    if (length > 0 && str.endsWith("\""))
    {
        str = str.substring(0, length - 1);
    }
    
    return str;
}
