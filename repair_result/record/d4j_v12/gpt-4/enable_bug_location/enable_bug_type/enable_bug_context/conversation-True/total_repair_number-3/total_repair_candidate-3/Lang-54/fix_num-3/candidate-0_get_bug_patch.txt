public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    Set<Locale> availableLocales = availableLocaleSet();

    int len = str.length();
    if (len < 2) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    // Case for language only: e.g. "fr"
    if (len == 2) {
        char ch0 = str.charAt(0);
        char ch1 = str.charAt(1);
        if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        Locale locale = new Locale(str);
        if (!availableLocales.contains(locale)) {
            throw new IllegalArgumentException("Locale not available: " + str);
        }
        return locale;
    }

    // Otherwise must start with language_...
    if (str.charAt(2) != '_') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    char ch0 = str.charAt(0);
    char ch1 = str.charAt(1);
    if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    // Possible cases: language_country or language_country_variant
    // language_country: length 5 (e.g. "fr_FR")
    // language_country_variant: length >= 7 (e.g. "fr_FR_POSIX" or "fr__POSIX")
    if (len == 3) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    // Extract language and country parts
    String language = str.substring(0, 2);
    String country = "";
    String variant = "";

    if (len == 5) {
        // Format: ll_CC
        char ch3 = str.charAt(3);
        char ch4 = str.charAt(4);
        if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        country = str.substring(3, 5);
        Locale locale = new Locale(language, country);
        if (!availableLocales.contains(locale)) {
            throw new IllegalArgumentException("Locale not available: " + str);
        }
        return locale;
    } else if (len >= 7) {
        // Format could be language_country_variant or language__variant (empty country)
        if (str.charAt(3) == '_') {
            // country is empty
            country = "";
            variant = str.substring(4);
            if (variant.isEmpty()) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            Locale locale = new Locale(language, country, variant);
            if (!availableLocales.contains(locale)) {
                // If not directly available, transparent checking is skipped here, still return it
                return locale;
            }
            return locale;
        } else {
            // country must be two uppercase letters
            char ch3 = str.charAt(3);
            char ch4 = str.charAt(4);
            if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            country = str.substring(3, 5);

            if (str.charAt(5) != '_') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            variant = str.substring(6);
            if (variant.isEmpty()) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            Locale locale = new Locale(language, country, variant);
            if (!availableLocales.contains(locale)) {
                // If not directly available, transparent checking is skipped here, still return it
                return locale;
            }
            return locale;
        }
    } else {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
}
