public String getValue() {
    if (isBooleanAttribute() && val == null) {
        return "";
    }
    return val;
}
