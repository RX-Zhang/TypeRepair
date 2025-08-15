public static boolean areEqual(Object o1, Object o2) {
    if (o1 == null || o2 == null) {
        return o1 == null && o2 == null;
    } else if (isArray(o1)) {
        if (!isArray(o2)) return false;
        try {
            return areArraysEqual(o1, o2);
        } catch (RuntimeException e) {
            // In case of any runtime exceptions during array equality checking, return false
            return false;
        }
    } else {
        try {
            return o1.equals(o2);
        } catch (RuntimeException e) {
            // Defensive: if equals throws exception, consider not equal
            return false;
        }
    }
}
