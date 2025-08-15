public static boolean equals(CharSequence cs1, CharSequence cs2) {
    if (cs1 == cs2) {
        return true;
    }
    if (cs1 == null || cs2 == null) {
        return false;
    }
    if (length(cs1) != length(cs2)) {
        return false;
    }
    int diff = indexOfDifference(cs1, cs2);
    return diff == -1 || diff == INDEX_NOT_FOUND;
}
