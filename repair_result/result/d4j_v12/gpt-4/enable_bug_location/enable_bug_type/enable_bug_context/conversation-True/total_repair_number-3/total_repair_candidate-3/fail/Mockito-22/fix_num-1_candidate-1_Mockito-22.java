public static boolean areEqual(Object o1, Object o2) {
    if (o1 == null || o2 == null) {
        return o1 == null && o2 == null;
    } 
    try {
        if (isArray(o1)) {
            if (!isArray(o2)) {
                return false;
            }
            return areArraysEqual(o1, o2);
        } else {
            return o1.equals(o2);
        }
    } catch (Exception e) {
        // In case any exception occurs (e.g. BadEquals), return false safely
        return false;
    }
}
