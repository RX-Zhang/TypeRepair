static String stripLeadingAndTrailingQuotes(String str)
{
    if (str == null || str.length() < 2) {
        return str;
    }
    
    if (str.startsWith("\"") && str.endsWith("\""))
    {
        str = str.substring(1, str.length() - 1);
    }
    
    return str;
}
