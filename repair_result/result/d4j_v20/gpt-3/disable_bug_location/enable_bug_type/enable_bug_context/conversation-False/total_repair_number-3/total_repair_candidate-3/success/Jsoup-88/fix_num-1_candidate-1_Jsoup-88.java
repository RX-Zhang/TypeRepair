public String getValue() {
    return isBooleanAttribute() && val == null ? "" : val;
}
