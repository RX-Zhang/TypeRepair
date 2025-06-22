public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }

    int len = str.length();
    if (len < 2) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    // Language must be 2 lowercase letters
    char ch0 = str.charAt(0);
    char ch1 = str.charAt(1);
    if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    if (len == 2) {
        Locale locale = new Locale(str, "");
        if (!availableLocaleSet().contains(locale)) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        return locale;
    }

    if (str.charAt(2) != '_') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    if (len == 5) {
        // country must be uppercase letters A-Z
        char ch3 = str.charAt(3);
        char ch4 = str.charAt(4);
        if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        Locale locale = new Locale(str.substring(0, 2), str.substring(3, 5));
        if (!availableLocaleSet().contains(locale)) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        return locale;
    }

    // len >= 7
    if (str.charAt(5) != '_') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    // language (2), country (2), variant (>=1)
    char ch3 = str.charAt(3);
    char ch4 = str.charAt(4);
    if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    String variant = str.substring(6);
    if (variant.isEmpty()) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    Locale locale = new Locale(str.substring(0, 2), str.substring(3, 5), variant);
    if (!availableLocaleSet().contains(locale)) {
        // Allow variants that may not be in available locales, but still valid
        // so skip check for variant locales presence since variants can be user-defined.
        // If strict checking needed, uncomment below line:
        // throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    return locale;
}
