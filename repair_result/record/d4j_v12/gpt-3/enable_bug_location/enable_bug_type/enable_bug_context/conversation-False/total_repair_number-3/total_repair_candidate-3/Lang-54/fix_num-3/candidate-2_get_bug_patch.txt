public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    int len = str.length();
    if (len != 2 && len != 5 && len != 7 && len != 8) {
        // Accept lengths: 2 (language), 5 (lang_country), 7 or 8 (lang__variant or lang_country_variant)
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
            char ch3 = str.charAt(3);
            char ch4 = str.charAt(4);
            if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            return new Locale(str.substring(0, 2), str.substring(3, 5));
        } else if (len >= 7) {
            // Two cases:
            // 1. "fr__POSIX" -> lang + __ + variant (empty country)
            // 2. "fr_FR_POSIX" -> lang + _ + country + _ + variant

            if (str.charAt(3) == '_') {
                // lang__variant: empty country
                String variant = str.substring(4);
                if (variant.isEmpty()) {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                return new Locale(str.substring(0, 2), "", variant);
            } else {
                // lang_country_variant
                char ch3 = str.charAt(3);
                char ch4 = str.charAt(4);
                if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                if (str.charAt(5) != '_') {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                String variant = str.substring(6);
                if (variant.isEmpty()) {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                return new Locale(str.substring(0, 2), str.substring(3, 5), variant);
            }
        } else {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
    }
}
