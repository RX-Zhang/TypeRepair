public static boolean containsIgnoreCase(String str, String searchStr) {
    if (str == null || searchStr == null) {
        return false;
    }
    // Using Locale.ROOT to ensure locale-independent uppercase conversion
    return contains(str.toUpperCase(Locale.ROOT), searchStr.toUpperCase(Locale.ROOT));
}
