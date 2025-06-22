public static boolean isAvailableLocale(Locale locale) {
    Set set = cAvailableLocaleSet;
    if (set == null) {
        set = new HashSet(availableLocaleList());
        set = Collections.unmodifiableSet(set);
        cAvailableLocaleSet = set;
    }
    return set.contains(locale);
}
