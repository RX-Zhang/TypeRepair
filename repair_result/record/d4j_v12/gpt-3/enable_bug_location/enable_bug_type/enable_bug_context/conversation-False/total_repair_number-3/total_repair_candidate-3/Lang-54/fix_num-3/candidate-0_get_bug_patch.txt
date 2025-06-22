public static Locale toLocale(String str) {
    if (str == null) {
        return null;
    }
    int len = str.length();
    if (len != 2 && len != 5 && len != 7 && len < 7) {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    char ch0 = str.charAt(0);
    char ch1 = str.charAt(1);
    if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
        throw new IllegalArgumentException("Invalid locale format: " + str);
    }
    if (len == 2) {
        return new Locale(str, "");
    } else if (len >= 5) {
        if (str.charAt(2) != '_') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        // Handle empty country code case: e.g. "fr__POSIX"
        String country = "";
        String variant = "";
        if (len == 5) {
            char ch3 = str.charAt(3);
            char ch4 = str.charAt(4);
            if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            country = str.substring(3, 5);
            return new Locale(str.substring(0, 2), country);
        } else {
            // len > 5
            if (str.charAt(3) == '_') {
                // country is empty, variant starts at index 4
                variant = str.substring(4);
                return new Locale(str.substring(0, 2), "", variant);
            } else {
                // country is not empty, variant starts at index 6
                char ch3 = str.charAt(3);
                char ch4 = str.charAt(4);
                if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                if (str.charAt(5) != '_') {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                variant = str.substring(6);
                return new Locale(str.substring(0, 2), str.substring(3, 5), variant);
            }
        }
    }
    throw new IllegalArgumentException("Invalid locale format: " + str);
}
