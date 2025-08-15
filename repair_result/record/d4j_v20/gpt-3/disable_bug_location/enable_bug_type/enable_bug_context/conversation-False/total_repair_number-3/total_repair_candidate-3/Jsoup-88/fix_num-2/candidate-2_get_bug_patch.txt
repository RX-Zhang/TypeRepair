public String getValue() {
    // If the attribute is a boolean attribute and val is null, return empty string
    if (isBooleanAttribute() && val == null) {
        return "";
    }
    return val;
}
