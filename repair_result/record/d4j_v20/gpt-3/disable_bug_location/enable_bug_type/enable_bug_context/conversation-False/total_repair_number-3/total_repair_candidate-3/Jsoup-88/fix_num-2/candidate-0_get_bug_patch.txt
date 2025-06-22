public String getValue() {
    if (val == null && isBooleanAttribute()) {
        return "";
    }
    return val;
}
