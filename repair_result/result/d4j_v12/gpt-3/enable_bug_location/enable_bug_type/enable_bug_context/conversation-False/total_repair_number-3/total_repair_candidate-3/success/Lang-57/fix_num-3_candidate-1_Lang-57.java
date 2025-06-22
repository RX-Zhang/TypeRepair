public static boolean isAvailableLocale(Locale locale) {
    if (locale == null) {
        return false; // Handle null case to prevent NullPointerException
    }
    return cAvailableLocaleSet != null && cAvailableLocaleSet.contains(locale);
}
