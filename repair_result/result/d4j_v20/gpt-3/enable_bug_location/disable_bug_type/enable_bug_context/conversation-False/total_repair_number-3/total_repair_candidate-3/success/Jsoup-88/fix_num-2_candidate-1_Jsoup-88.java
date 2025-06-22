public String getValue() {
    return val == null && isBooleanAttribute() ? "" : val;
}
