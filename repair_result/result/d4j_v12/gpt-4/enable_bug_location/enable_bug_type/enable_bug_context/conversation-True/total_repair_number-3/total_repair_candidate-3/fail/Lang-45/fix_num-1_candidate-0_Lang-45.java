public static String abbreviate(String str, int lower, int upper, String appendToEnd) {
    // initial parameter checks
    if (str == null) {
        return null;
    }
    if (str.length() == 0) {
        return StringUtils.EMPTY;
    }

    // adjust lower and upper bounds to valid range
    if (lower < 0) {
        lower = 0;
    }
    if (upper == -1 || upper > str.length()) {
        upper = str.length();
    }
    if (lower > str.length()) {
        lower = str.length();
    }
    if (upper < lower) {
        upper = lower;
    }

    StringBuffer result = new StringBuffer();
    int index = StringUtils.indexOf(str, " ", lower);
    if (index == -1 || index > upper) {
        result.append(str.substring(0, upper));
        // only if abbreviation has occured do we append the appendToEnd value
        if (upper != str.length()) {
            result.append(StringUtils.defaultString(appendToEnd));
        }
    } else {
        result.append(str.substring(0, index));
        resultUtils.defaultString(appendToEnd));
    }
    return result.toString();
}
