public static String abbreviate(String str, int lower, int upper, String appendToEnd) {
    // initial parameter checks
    if (str == null) {
        return null;
    }
    if (str.length() == 0) {
        return StringUtils.EMPTY;
    }

    // Check boundaries for lower and upper
    if (lower < 0) {
        lower = 0; // Reset lower to 0 if it's negative
    }
    if (lower > str.length()) {
        lower = str.length(); // If lower is greater than string length, set to the length
    }

    // if the upper value is -1 (i.e. no limit) or is greater
    // than the length of the string, set to the length of the string
    if (upper == -1 || upper > str.length()) {
        upper = str.length();
    }
    // if upper is less than lower, raise it to lower
    if (upper < lower) {
        upper = lower;
    }

    StringBuffer result = new StringBuffer();
    int index = StringUtils.indexOf(str, " ", lower);
    if (index == -1) {
        result.append(str.substring(lower, upper)); // Use lower instead of 0
        // only if abbreviation has occurred do we append the appendToEnd value
        if (upper != str.length()) {
            result.append(StringUtils.defaultString(appendToEnd));
        }
    } else if (index > upper) {
        result.append(str.substring(lower, upper)); // Use lower instead of 0
        result.append(StringUtils.defaultString(appendToEnd));
    } else {
        result.append(str.substring(lower, index)); // Use lower instead of 0
        result.append(StringUtils.defaultString(appendToEnd));
    }
    return result.toString();
}
