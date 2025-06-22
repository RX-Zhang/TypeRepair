public static boolean containsIgnoreCase(String str, String searchStr) {
    if (str == null || searchStr == null) {
        return false;
    }
    return contains(str.toLowerCase(Locale.ROOT), searchStr.toLowerCase(Locale.ROOT));
}
