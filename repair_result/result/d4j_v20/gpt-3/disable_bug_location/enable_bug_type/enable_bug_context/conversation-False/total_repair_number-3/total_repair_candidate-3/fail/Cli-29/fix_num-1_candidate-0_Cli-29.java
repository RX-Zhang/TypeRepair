static String stripLeadingAndTrailingQuotes(String str)
{
    if (str == null || str.length() == 0) {
        return str;
    }
    
    int start = 0;
    int end = str.length();

    if (str.startsWith("\"") && str.endsWith("\"") && str.length() > 1) {
        start = 1;
        end = end - 1;
    }

    return str.substring(start, end);
}
