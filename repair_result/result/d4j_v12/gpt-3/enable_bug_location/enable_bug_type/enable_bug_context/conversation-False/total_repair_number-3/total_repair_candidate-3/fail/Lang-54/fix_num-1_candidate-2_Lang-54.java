public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    int len = str.length();
    if (len != 2 && len != 5 && len != 7 && len != 8) { // fixed length check; allow length 7 or 8 for variants like fr__POSIX
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
            // pattern: ll_CC
            char ch3 = str.charAt(3);
            char ch4 = str.charAt(4);
            if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            return new Locale(str.substring(0, 2), str.substring(3, 5));
        } else if (len >= 7) {
            // Could be ll__VVVV or ll_CC_VVVV
            if (str.charAt(3) == '_') { // ll__variant
                String variant = str.substring(4);
                if (variant.isEmpty()) {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                return new Locale(str.substring(0, 2), "", variant);
            } else {
                // ll_CC_variant
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
