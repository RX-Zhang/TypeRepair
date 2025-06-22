public static boolean isAvailableLocale(Locale locale) {
    if (locale == null) {
        return false; // Add boundary check for null input
    }
    return cAvailableLocaleSet != null && cAvailableLocaleSet.contains(locale);
}
