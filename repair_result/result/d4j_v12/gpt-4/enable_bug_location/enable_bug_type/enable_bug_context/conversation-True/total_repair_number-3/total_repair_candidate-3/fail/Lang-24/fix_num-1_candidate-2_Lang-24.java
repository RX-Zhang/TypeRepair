public static boolean isNumber(String str) {
    if (StringUtils.isEmpty(str)) {
        return false;
    }
    try {
        Number createNumber = createNumber(str);
        return createNumber != null;
    } catch (NumberFormatException nfe) {
        return false;
    }
}
