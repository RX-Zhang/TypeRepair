public static boolean isAvailableLocale(Locale locale) {
    Set set = cAvailableLocaleSet;
    if (set == null) {
        set = availableLocaleSet();
    }
    return set.contains(locale);
}
