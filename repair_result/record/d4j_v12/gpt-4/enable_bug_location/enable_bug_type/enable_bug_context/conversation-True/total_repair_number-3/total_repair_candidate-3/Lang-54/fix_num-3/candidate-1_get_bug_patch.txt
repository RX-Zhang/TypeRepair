public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    int len = str.length();

    // Validate length and format for language only, language_country, or language_country_variant
    if (len < 2) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    // Language must be 2 lowercase letters
    char ch0 = str.charAt(0);
    char ch1 = str.charAt(1);
    if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    // If only language
    if (len == 2) {
        Locale locale = new Locale(str, "");
        if (!availableLocaleSet().contains(locale)) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        return locale;
    }

    // For longer strings, expect _ after language code
    if (str.charAt(2) != '_') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    // If length is 5 (e.g. "fr_FR")
    if (len == 5) {
        char ch3 = str.charAt(3);
        char ch4 = str.charAt(4);
        if ((ch3 < 'A' || ch3 > 'Z') && (ch3 < 'a' || ch3 > 'z')) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if ((ch4 < 'A' || ch4 > 'Z') && (ch4 < 'a' || ch4 > 'z')) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        String country = str.substring(3, 5);
        Locale locale = new Locale(str.substring(0, 2), country);
        if (!availableLocaleSet().contains(locale)) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        return locale;
    }

    // For longer length (>=7), must be language_country_variant format like fr__POSIX
    if (len >= 7) {
        // Check if "_" is at position 3 or 4 (to accept cases such as fr__POSIX where country is empty)
        // The standard format is language_country_variant, but country can be empty
        if (str.charAt(3) != '_') {
            // country code expected at positions 3 and 4
            char ch3 = str.charAt(3);
            char ch4 = str.charAt(4);
            if ((ch3 < 'A' || ch3 > 'Z') && (ch3 < 'a' || ch3 > 'z')) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            if ((ch4 < 'A' || ch4 > 'Z') && (ch4 < 'a' || ch4 > 'z')) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            if (str.charAt(5) != '_') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            String language = str.substring(0,2);
            String country = str.substring(3,5);
            String variant = str.substring(6);
            Locale locale = new Locale(language, country, variant);
            if (!availableLocaleSet().contains(locale) && !variant.equals("")) {
                // allow variant without official pre-registered locale, e.g. POSIX
                // so do not throw here for variant locales, just accept them
                return locale;
            }
            return locale;
        } else {
            // country is empty, variant follows at position 4+
            String language = str.substring(0,2);
            String country = "";
            String variant = str.substring(4);
            Locale locale = new Locale(language, country, variant);
            // Accept such locales (like fr__POSIX)
            return locale;
        }
    }

    throw new IllegalArgumentException("Invalid locale format: " + str);
}
