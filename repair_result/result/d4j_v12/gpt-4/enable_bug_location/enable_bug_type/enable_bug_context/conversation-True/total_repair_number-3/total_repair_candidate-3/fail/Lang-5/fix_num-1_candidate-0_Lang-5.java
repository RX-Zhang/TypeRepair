public static Locale toLocale(final String str) {
    if (str == null) {
        return null;
    }
    final int len = str.length();
    if (len == 0) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    if (str.charAt(0) == '_') {
        // Cases starting with underscore
        if (len < 3) {
            throw new IllegalArgumentException("Must be at least 3 characters if starts with underscore: " + str);
        }
        final char ch1 = str.charAt(1);
        final char ch2 = str.charAt(2);
        if (!Character.isUpperCase(ch1) || !Character.isUpperCase(ch2)) {
            throw new IllegalArgumentException("Country code must be uppercase letters if starts with underscore: " + str);
        }
        if (len == 3) {
            return new Locale("", str.substring(1, 3));
        }
        if (len == 4) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (str.charAt(3) != '_') {
            throw new IllegalArgumentException("Must have underscore after country if started with underscore and length >= 5: " + str);
        }
        // Now parse variant
        String variant = str.substring(4);
        return new Locale("", str.substring(1, 3), variant);
    } else {
        // Normal cases
        final char ch0 = str.charAt(0);
        final char ch1 = (len > 1) ? str.charAt(1) : '\0';
        if (!Character.isLowerCase(ch0) || (len > 1 && !Character.isLowerCase(ch1))) {
            throw new IllegalArgumentException("Language must be lowercase letters: " + str);
        }
        if (len == 1 {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (len == 2) {
            return new Locale(str);
        }
        if (len < 5) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (str.charAt(2) != '_') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        final char ch3 = str.charAt(3);
        if (ch3 == '_') {
            return new Locale(str.substring(0, 2), "", str.substring(4));
        }
        final char ch4 = str.charAt(4);
        if (!Character.isUpperCase(ch3) || !Character.isUpperCase(ch4)) {
            throw new IllegalArgumentException("Country code must be uppercase letters: " + str);
        }
        if (len == 5) {
            return new Locale(str.substring(0, 2), str.substring(3, 5));
        }
        if (len < 7) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (str.charAt(5) != '_') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        return new Locale(str.substring(0, 2), str.substring(3, 5), str.substring(6));
    }
}
