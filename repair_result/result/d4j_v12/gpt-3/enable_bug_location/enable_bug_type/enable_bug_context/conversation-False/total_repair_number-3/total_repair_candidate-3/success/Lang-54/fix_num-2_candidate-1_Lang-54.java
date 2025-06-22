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
        // Handle language + country + variant or language + "" + variant (e.g. fr__POSIX)
        // Check if the next two chars (at pos 3 and 4) represent the country or are underscores
        
        char ch3 = str.charAt(3);
        if (ch3 == '_') {
            // case like fr__POSIX => language = fr, country = "", variant = substring from 4 onwards
            // variant starts at 4 (after two underscores), but we must ensure str length > 4
            if (len < 5) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            String language = str.substring(0, 2);
            String country = "";
            String variant = str.substring(4);
            return new Locale(language, country, variant);
        } else {
            // If ch3 is not '_', it must be uppercase letter (country code first char)
            if (ch3 < 'A' || ch3 > 'Z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            if (len < 5) {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            char ch4 = str.charAt(4);
            if (ch4 < 'A' || ch4 > 'Z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            if (len == 5) {
                return new Locale(str.substring(0, 2), str.substring(3, 5));
            } else {
                if (str.charAt(5) != '_') {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                String language = str.substring(0, 2);
                String country = str.substring(3, 5);
                String variant = str.substring(6);
                return new Locale(language, country, variant);
            }
        }
    }
}
