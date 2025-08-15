static String stripLeadingAndTrailingQuotes(String str)
{
    if (str == null || str.length() == 0) {
        return str;
    }
    
    boolean startsWithQuote = str.startsWith("\"");
    boolean endsWithQuote = str.endsWith("\"");
    
    if (startsWithQuote && endsWithQuote && str.length() > 1)
    {
        str = str.substring(1, str.length() - 1);
    }
    
    return str;
}
