public static boolean containsIgnoreCase(String str, String searchStr) {
    if (str == null || searchStr == null) {
        return false;
    }
    // Use Locale.ROOT to avoid locale-specific upper case mappings
    return contains(str.toUpperCase(Locale.ROOT), searchStr.toUpperCase(Locale.ROOT));
}
