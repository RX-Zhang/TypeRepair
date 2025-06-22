static String stripLeadingAndTrailingQuotes(String str)
{
    if (str == null || str.isEmpty()) {
        return str;
    }

    int start = 0;
    int end = str.length();

    if (str.startsWith("\"")) {
        start++;
    }
    if (str.endsWith("\"") && end > start) {
        end--;
    }

    return str.substring(start, end);
}
