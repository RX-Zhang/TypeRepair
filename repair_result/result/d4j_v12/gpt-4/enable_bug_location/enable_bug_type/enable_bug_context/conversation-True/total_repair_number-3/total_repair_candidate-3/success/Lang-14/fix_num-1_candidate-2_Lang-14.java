public static boolean equals(CharSequence cs1, CharSequence cs2) {
    if (cs1 == cs2) {
        return true;
    }
    if (cs1 == null || cs2 == null) {
        return false;
    }
    int diff = indexOfDifference(cs1, cs2);
    return diff == StringUtils.INDEX_NOT_FOUND;
}
