public static boolean isAvailableLocale(Locale locale) {
    if (locale == null) {
        throw new IllegalArgumentException("Locale cannot be null");
    }
    return cAvailableLocaleSet != null && cAvailableLocaleSet.contains(locale);
}
