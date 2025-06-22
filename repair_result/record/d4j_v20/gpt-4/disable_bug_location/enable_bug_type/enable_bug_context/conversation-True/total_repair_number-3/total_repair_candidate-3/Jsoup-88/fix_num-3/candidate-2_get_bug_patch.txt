public String getValue() {
    // If this is a boolean attribute and its value is null, return an empty string
    if (isBooleanAttribute() && val == null) {
        return "";
    }
    return val;
}
