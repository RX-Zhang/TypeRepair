static String stripLeadingHyphens(String str)
{
    if (str == null) {
        return null; // Validate input to handle null case
    }
    if (str.startsWith("--")) {
        return str.substring(2);
    } else if (str.startsWith("-")) {
        return str.substring(1);
    }

    return str;
}
