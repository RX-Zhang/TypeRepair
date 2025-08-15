public static boolean isNumber(String str) {
    if (StringUtils.isEmpty(str)) {
        return false;
    }
    try {
        Number number = createNumber(str);
        return number != null;
    } catch (NumberFormatException nfe) {
        return false;
    }
}
