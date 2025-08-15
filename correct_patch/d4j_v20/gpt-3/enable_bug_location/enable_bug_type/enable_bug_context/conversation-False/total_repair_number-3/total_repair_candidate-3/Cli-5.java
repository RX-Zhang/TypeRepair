static String stripLeadingHyphens(String str)
{
    if (str == null) {
        return null; // Handle null input
    }
    if (str.startsWith("--"))
    {
        return str.substring(2, str.length());
    }
    else if (str.startsWith("-"))
    {
        return str.substring(1, str.length());
    }

    return str;
}
