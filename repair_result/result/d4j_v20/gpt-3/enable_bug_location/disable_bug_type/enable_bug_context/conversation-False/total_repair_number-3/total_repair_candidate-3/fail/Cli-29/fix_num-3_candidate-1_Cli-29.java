static String stripLeadingAndTrailingQuotes(String str)
{
    if (str == null || str.length() == 0) {
        return str;
    }
    
    if (str.length() == 1 && str.equals("\"")) {
        return str;
    }
    
    if (str.startsWith("\""))
    {
        str = str.substring(1);
    }
    
    if (str.endsWith("\""))
    {
        str = str.substring(0, str.length() - 1);
    }
    
    return str;
}
