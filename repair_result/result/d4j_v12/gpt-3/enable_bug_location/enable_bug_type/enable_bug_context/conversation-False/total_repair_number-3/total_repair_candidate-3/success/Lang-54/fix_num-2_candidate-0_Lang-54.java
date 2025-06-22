public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    int len = str.length();
    if (len != 2 && len != 5 && len < 7) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    char ch0 = str.charAt(0);
    char ch1 = str.charAt(1);
    if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    if (len == 2) {
        return new Locale(str, "");
    } else {
        if (str.charAt(2) != '_') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (len == 5) {
            // Country code must be uppercase letters or an empty string (for cases like "fr__POSIX")
            char ch3 = str.charAt(3);
            char ch4 = str.charAt(4);
            if ((ch3 < 'A' || ch3 > 'Z') || (ch4 < 'A' || ch4 > 'Z')) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            return new Locale(str.substring(0, 2), str.substring(3, 5));
        } else {
            // Support "fr__POSIX" pattern (language + empty country + variant)
            if (str.charAt(3) == '_' ) {
                // Locale like: xx__variant
                String language = str.substring(0, 2);
                String country = "";
                String variant = str.substring(4);
                if (variant.isEmpty()) {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                return new Locale(language, country, variant);
            } else {
                // Otherwise, expect language_country_variant format (e.g. "fr_FR_POSIX")
                char ch3 = str.charAt(3);
                char ch4 = str.charAt(4);
                if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                if (str.charAt(5) != '_') {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                String language = str.substring(0, 2);
                String country = str.substring(3, 5);
                String variant = str.substring(6);
                if (variant.isEmpty()) {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                return new Locale(language, country, variant);
            }
        }
    }
}
