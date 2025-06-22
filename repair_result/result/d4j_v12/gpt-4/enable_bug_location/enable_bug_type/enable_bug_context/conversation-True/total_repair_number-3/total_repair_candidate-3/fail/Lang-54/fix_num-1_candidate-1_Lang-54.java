public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    int len = str.length();
    // Valid lengths: 2 (language), 5 (language_country), 7 or more (language_country_variant)
    if (len < 2) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    char ch0 = str.charAt(0);
    char ch1 = str.charAt(1);
    // Language must be lowercase letters
    if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    if (len == 2) {
        Locale locale = new Locale(str, "");
        if (!availableLocaleSet().contains(locale)) {
            return locale;  // Accept even if not in availableLocaleSet to allow undefined locales
        }
        return locale;
    }
    if (str.charAt(2) != '_') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    if (len == 5) {
        char ch3 = str.charAt(3);
        char ch4 = str.charAt(4);
        // Country must be uppercase letters
        if ((ch3 < 'A' || ch3 > 'Z') && (ch3 != ' ')) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if ((ch4 < 'A' || ch4 > 'Z') && (ch4 != ' ')) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        Locale locale = new Locale(str.substring(0, 2), str.substring(3, 5));
        if (!availableLocaleSet().contains(locale)) {
            return locale;  // Accept even if not in availableLocaleSet
        }
        return locale;
    }
    if (str.charAt(5) != '_') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    String language = str.substring(0, 2);
    String country = str.substring(3, 5);
    String variant = str.substring(6);
    if (language.length() != 2 || country.length() != 2) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    // Validate language letters
    if (!language.chars().allMatch(ch -> ch >= 'a' && ch <= 'z')) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    // Validate country letters
    if (!country.chars().allMatch(ch -> (ch >= 'A' && ch <= 'Z') || ch == ' ')) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    if (variant.isEmpty()) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    Locale locale = new Locale(language, country, variant);
    return locale;
}
