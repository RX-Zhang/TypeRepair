public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    int len = str.length();
    if (len < 2) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    char ch0 = str.charAt(0);
    char ch1 = str.charAt(1);
    // Language code validation: must be lowercase letters
    if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    if (len == 2) {
        Locale locale = new Locale(str, "");
        if (!isAvailableLocale(locale)) {
            throw new IllegalArgumentException("Locale not available: " + str);
        }
        return locale;
    }

    if (str.charAt(2) != '_') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    if (len == 3) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    char ch3 = str.charAt(3);

    // Handle the case for language_country or language__variant (empty country)
    if (ch3 == '_') {
        // variant only, language and empty country
        String language = str.substring(0, 2);
        String country = "";
        String variant = str.substring(4);
        if (variant.isEmpty()) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        Locale locale = new Locale(language, country, variant);
        if (!isAvailableLocale(locale)) {
            throw new IllegalArgumentException("Locale not available: " + str);
        }
        return locale;
    }

    // Country code validation: must be uppercase letters
    if (ch3 < 'A' || ch3 > 'Z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    if (len == 4) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    char ch4 = str.charAt(4);
    if (ch4 < 'A' || ch4 > 'Z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    if (len == 5) {
        Locale locale = new Locale(str.substring(0, 2), str.substring(3, 5));
        if (!isAvailableLocale(locale)) {
            throw new IllegalArgumentException("Locale not available: " + str);
        }
        return locale;
    }

    if (str.charAt(5) != '_') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    String variant = str.substring(6);
    if (variant.isEmpty()) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    Locale locale = new Locale(str.substring(0, 2), str.substring(3, 5), variant);
    if (!isAvailableLocale(locale)) {
        throw new IllegalArgumentException("Locale not available: " + str);
    }
    return locale;
}
