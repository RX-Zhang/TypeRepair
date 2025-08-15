public static boolean isNumber(String str) {
    if (StringUtils.isEmpty(str)) {
        return false;
    }
    try {
        Number num = createNumber(str);
        return num != null;
    } catch (NumberFormatException nfe) {
        return false;
    }
}
