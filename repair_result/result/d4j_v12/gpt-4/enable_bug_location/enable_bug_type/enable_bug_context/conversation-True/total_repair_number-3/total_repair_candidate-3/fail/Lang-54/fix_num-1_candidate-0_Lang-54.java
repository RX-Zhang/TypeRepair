public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    // Validate length and format more flexibly to accommodate empty country or variant
    int len = str.length();
    if (len < 2) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    String language = "";
    String country = "";
    String variant = "";

    String[] parts = str.split("_", -1); // keep empty trailing tokens

    if (parts.length > 3) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    language = parts[0];
    if (language.length() != 2 || !language.chars().allMatch(ch -> ch >= 'a' && ch <= 'z')) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    if (parts.length > 1) {
        country = parts[1];
        // country can be "" or 2 uppercase letters
        if (country.length() != 0 && (country.length() != 2 || !country.chars().allMatch(ch -> ch >= 'A' && ch <= 'Z'))) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
    }

    if (parts.length == 3) {
        variant = parts[2];
        if (variant.length() == 0) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
    }

    Locale locale = new Locale(language, country, variant);

    // Validate that the constructed locale is among the available locales
    if (!availableLocaleSet().contains(locale)) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }

    return locale;
}
