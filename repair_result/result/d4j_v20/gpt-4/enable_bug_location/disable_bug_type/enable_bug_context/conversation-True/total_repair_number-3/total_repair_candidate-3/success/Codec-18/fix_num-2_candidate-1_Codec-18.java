public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
    if (cs1 == cs2) {
        return true;
    }
    if (cs1 == null || cs2 == null) {
        return false;
    }
    if (cs1 instanceof String && cs2 instanceof String) {
        return cs1.equals(cs2);
    }
    int length1 = cs1.length();
    int length2 = cs2.length();
    if (length1 != length2) {
        return false;
    }
    return CharSequenceUtils.regionMatches(cs1, false, 0, cs2, 0, length1);
}
