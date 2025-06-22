public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    int len = str.length();
    if (len < 2) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    // Language code should be 2 lowercase letters
    char ch0 = str.charAt(0);
    char ch1 = str.charAt(1);
    if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    if (len == 2) {
        Locale locale = new Locale(str, "");
        if (!isAvailableLocale(locale)) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        return locale;
    }
    if (str.charAt(2) != '_') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    if (len == 3) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    // At least language + '_' + something, check next part
    // If next part is country (2 uppercase letters)
    char ch3 = str.charAt(3);
    if (len == 5) {
        char ch4 = str.charAt(4);
        if (ch3 == '_') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        Locale locale = new Locale(str.substring(0, 2), str.substring(3, 5));
        if (!isAvailableLocale(locale)) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        return locale;
    }
    if (len > 5) {
        if (ch3 == '_') {
            // Language + "" + variant, no country
            String variant = str.substring(4);
            if (variant.isEmpty()) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            Locale locale = new Locale(str.substring(0, 2), "", variant);
            if (!isAvailableLocale(locale)) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            return locale;
        } else {
            // language + country + variant
            if (len < 7) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            char ch4 = str.charAt(4);
            if (ch4 < 'A' || ch4 > 'Z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
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
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            return locale;
        }
    }
    throw new IllegalArgumentException("Invalid locale format: " + str);
}
