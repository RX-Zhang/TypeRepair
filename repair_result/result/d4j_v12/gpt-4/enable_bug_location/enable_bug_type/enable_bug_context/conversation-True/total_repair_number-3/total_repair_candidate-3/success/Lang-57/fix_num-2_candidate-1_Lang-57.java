public static boolean isAvailableLocale(Locale locale) {
    Set set = availableLocaleSet();
    return set.contains(locale);
}
