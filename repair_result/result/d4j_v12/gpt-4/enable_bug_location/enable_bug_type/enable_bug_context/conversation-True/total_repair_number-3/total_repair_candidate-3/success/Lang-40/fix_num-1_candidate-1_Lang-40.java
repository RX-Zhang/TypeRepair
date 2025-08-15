public static boolean containsIgnoreCase(String str, String searchStr) {
    if (str == null || searchStr == null) {
        return false;
    }
    final int length = searchStr.length();
    final int max = str.length() - length;
    for (int i = 0; i <= max; i++) {
        if (str.regionMatches(true, i, searchStr, 0, length)) {
            return true;
        }
    }
    return false;
}
