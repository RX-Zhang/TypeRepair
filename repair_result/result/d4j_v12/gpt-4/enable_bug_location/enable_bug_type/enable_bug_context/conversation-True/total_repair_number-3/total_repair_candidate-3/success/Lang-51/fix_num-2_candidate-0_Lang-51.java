public static boolean toBoolean(String str) {
    if (str == null) {
        return false;
    }
    Boolean boolObj = toBooleanObject(str);
    return toBoolean(boolObj);
}
