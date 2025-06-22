public static boolean areEqual(Object o1, Object o2) {
    if (o1 == null || o2 == null) {
        return o1 == null && o2 == null;
    } else if (isArray(o1)) {
        if (!isArray(o2)) {
            return false;
        }
        if (Array.getLength(o1) != Array.getLength(o2)) {
            return false;
        }
        for (int i = 0; i < Array.getLength(o1); i++) {
            Object e1 = Array.get(o1, i);
            Object e2 = Array.get(o2, i);
            // Defensive: catch potential exceptions from equals
            boolean equal;
            try {
                equal = areEqual(e1, e2);
            } catch (Exception ex) {
                return false;
            }
            if (!equal) {
                return false;
            }
        }
        return true;
    } else {
        // Defensive: catch any exception from equals
        try {
            return o1.equals(o2);
        } catch (Exception ex) {
            return false;
        }
    }
}
