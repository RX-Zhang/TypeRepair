public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }

    // Quick sanity check: empty or too short or too long strings are invalid
    int len = str.length();
    if (len < 2) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    // Handle language only: 2 or 3 lowercase letters
    if (len == 2 || len == 3) {
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch < 'a' || ch > 'z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
        }
        Locale locale = new Locale(str);
        // Validate against available locales if needed
        if (!availableLocaleSet().contains(locale)) {
            // Still allow creation but could throw if strict
            // throw new IllegalArgumentException("Invalid locale: " + str);
        }
        return locale;
    }
    
    // Parse based on underscore positions
    int firstUnderscore = str.indexOf('_');
    
    if (firstUnderscore == -1) {
        // No underscore but more than 3 chars, invalid format
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    
    String language = str.substring(0, firstUnderscore);
    
    if (language.length() < 2 || language.length() > 3) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    for (int i = 0; i < language.length(); i++) {
        char ch = language.charAt(i);
        if (ch < 'a' || ch > 'z') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
    }

    int secondUnderscore = str.indexOf('_', firstUnderscore + 1);

    if (secondUnderscore == -1) {
        // Format like language_COUNTRY
        String country = str.substring(firstUnderscore + 1);
        if (!(country.length() == 2 || country.length() == 3)) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }

        for (int i = 0; i < country.length(); i++) {
            char ch = country.charAt(i);
            if ((ch < 'A' || ch > 'Z') && (ch < '0' || ch > '9')) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
        }

        return new Locale(language, country);
    } else {
        // Format language_COUNTRY_VARIANT
        String country = str.substring(firstUnderscore + 1, secondUnderscore);
        String variant = str.substring(secondUnderscore + 1);

        if (!(country.length() == 2 || country.length() == 3)) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }

        for (int i = 0; i < country.length(); i++) {
            char ch = country.charAt(i);
            if ((ch < 'A' || ch > 'Z') && (ch < '0' || ch > '9')) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
        }

        // Variant can be any string including empty but should not be null
        if (variant.isEmpty()) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }

        return new Locale(language, country, variant);
    }
}
