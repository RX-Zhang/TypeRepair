static String stripLeadingAndTrailingQuotes(String str)
{
    if (str == null || str.length() == 0) {
        return str;
    }
    if (str.length() == 1 && str.equals("\"")) {
        return str;
    }
    if (str.startsWith("\"") && str.length() > 1)
    {
        str = str.substring(1);
    }
    int length = str.length();
    if (str.endsWith("\"") && length > 1)
    {
        str = str.substring(0, length - 1);
    }
    
    return str;
}
