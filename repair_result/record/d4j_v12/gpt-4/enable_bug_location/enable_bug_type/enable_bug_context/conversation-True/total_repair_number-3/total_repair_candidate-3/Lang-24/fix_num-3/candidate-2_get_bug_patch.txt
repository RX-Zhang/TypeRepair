public static boolean isNumber(String str) {
    if (StringUtils.isEmpty(str)) {
        return false;
    }
    try {
        Number n = createNumber(str);
        return n != null;
    } catch (NumberFormatException e) {
        return false;
    }
}
