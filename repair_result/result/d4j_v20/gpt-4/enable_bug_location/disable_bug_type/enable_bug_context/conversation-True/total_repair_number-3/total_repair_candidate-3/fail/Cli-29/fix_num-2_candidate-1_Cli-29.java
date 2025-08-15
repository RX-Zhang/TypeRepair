static String stripLeadingAndTrailingQuotes(String str)
{
    if (str == null || str.length() == 0) {
        return str;
    }
    
    if (str.length() == 1 && str.equals("\"")) {
        return str;
    }

    if (str.startsWith("\"") && str.endsWith("\"") && str.length() > 1)
    {
        str = str.substring(1, str.length() - 1);
    }
    
    return str;
}
